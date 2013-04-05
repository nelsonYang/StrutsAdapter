package com.struts.adapter.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.opensymphony.xwork2.util.PatternMatcher;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.upload.FormFile;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.interceptor.FileUploadInterceptor;

/**
 *
 * @author nelson.yang
 */
public class UploadFileInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = -4764627478894962478L;
    protected static final Logger LOG = LoggerFactory.getLogger(FileUploadInterceptor.class);
    private static final String DEFAULT_MESSAGE = "no.message.found";
    protected boolean useActionMessageBundle;
    protected Long maximumSize;
    protected Set<String> allowedTypesSet = Collections.emptySet();
    protected Set<String> allowedExtensionsSet = Collections.emptySet();
    private PatternMatcher matcher;

    @Inject
    public void setMatcher(PatternMatcher matcher) {
        this.matcher = matcher;
    }

    public void setUseActionMessageBundle(String value) {
        this.useActionMessageBundle = Boolean.valueOf(value);
    }

    /**
     * Sets the allowed extensions
     *
     * @param allowedExtensions A comma-delimited list of extensions
     */
    public void setAllowedExtensions(String allowedExtensions) {
        allowedExtensionsSet = TextParseUtil.commaDelimitedStringToSet(allowedExtensions);
    }

    /**
     * Sets the allowed mimetypes
     *
     * @param allowedTypes A comma-delimited list of types
     */
    public void setAllowedTypes(String allowedTypes) {
        allowedTypesSet = TextParseUtil.commaDelimitedStringToSet(allowedTypes);
    }

    /**
     * Sets the maximum size of an uploaded file
     *
     * @param maximumSize The maximum size in bytes
     */
    public void setMaximumSize(Long maximumSize) {
        this.maximumSize = maximumSize;
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
     */
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ActionContext ac = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);
        if (!(request instanceof MultiPartRequestWrapper)) {
            if (LOG.isDebugEnabled()) {
                ActionProxy proxy = invocation.getProxy();
                LOG.debug(getTextMessage("struts.messages.bypass.request", new Object[]{proxy.getNamespace(), proxy.getActionName()}, ac.getLocale()));
            }
            return invocation.invoke();
        }

        ValidationAware validation = null;

        Object action = invocation.getAction();

        if (action instanceof ValidationAware) {
            validation = (ValidationAware) action;
        }

        MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) request;

        if (multiWrapper.hasErrors()) {
            for (String error : multiWrapper.getErrors()) {
                if (validation != null) {
                    validation.addActionError(error);
                }

                if (LOG.isWarnEnabled()) {
                    LOG.warn(error);
                }
            }
        }

        // bind allowed Files

        Enumeration fileParameterNames = multiWrapper.getFileParameterNames();
        Map<String, Object> paramterMap = ac.getParameters();
        FormFile formFile;
        File file;
        List<FormFile> formFileList;
        while (fileParameterNames != null && fileParameterNames.hasMoreElements()) {
            formFileList = new ArrayList<FormFile>(2);
            String inputName = (String) fileParameterNames.nextElement();
            String[] contentType = multiWrapper.getContentTypes(inputName);
            if (isNonEmpty(contentType)) {
                // get the name of the file from the input tag
                String[] fileName = multiWrapper.getFileNames(inputName);
                if (isNonEmpty(fileName)) {
                    File[] files = multiWrapper.getFiles(inputName);
                    if (files != null && files.length > 0) {
                        List<File> acceptedFiles = new ArrayList<File>(files.length);
                        List<String> acceptedContentTypes = new ArrayList<String>(files.length);
                        List<String> acceptedFileNames = new ArrayList<String>(files.length);
                        for (int index = 0; index < files.length; index++) {
                            if (acceptFile(action, files[index], fileName[index], contentType[index], inputName, validation, ac.getLocale())) {
                                file = files[index];
                                acceptedFiles.add(files[index]);
                                acceptedContentTypes.add(contentType[index]);
                                acceptedFileNames.add(fileName[index]);
                                //封装成FormFile格式
                                formFile = new FormFile();
                                formFile.setInputName(inputName);
                                formFile.setContentType(contentType[0]);
                                formFile.setFile(file);
                                formFileList.add(formFile);
                            }
                        }
                        paramterMap.put(inputName, formFileList.toArray());

                    }
                } else {
                    if (LOG.isWarnEnabled()) {
                        LOG.warn(getTextMessage(action, "struts.messages.invalid.file", new Object[]{inputName}, ac.getLocale()));
                    }
                }
            } else {
                if (LOG.isWarnEnabled()) {
                    LOG.warn(getTextMessage(action, "struts.messages.invalid.content.type", new Object[]{inputName}, ac.getLocale()));
                }
            }
        }
        //   invocation.getInvocationContext().getContextMap().put(Constant.ACTION_FORM_FILE, formFileList);
        // invoke action
        return invocation.invoke();
    }

    /**
     * Override for added functionality. Checks if the proposed file is
     * acceptable based on contentType and size.
     *
     * @param action - uploading action for message retrieval.
     * @param file - proposed upload file.
     * @param contentType - contentType of the file.
     * @param inputName - inputName of the file.
     * @param validation - Non-null ValidationAware if the action implements
     * ValidationAware, allowing for better logging.
     * @param locale
     * @return true if the proposed file is acceptable by contentType and size.
     */
    protected boolean acceptFile(Object action, File file, String filename, String contentType, String inputName, ValidationAware validation, Locale locale) {
        boolean fileIsAcceptable = false;

        // If it's null the upload failed
        if (file == null) {
            String errMsg = getTextMessage(action, "struts.messages.error.uploading", new Object[]{inputName}, locale);
            if (validation != null) {
                validation.addFieldError(inputName, errMsg);
            }

            if (LOG.isWarnEnabled()) {
                LOG.warn(errMsg);
            }
        } else if (maximumSize != null && maximumSize < file.length()) {
            String errMsg = getTextMessage(action, "struts.messages.error.file.too.large", new Object[]{inputName, filename, file.getName(), "" + file.length()}, locale);
            if (validation != null) {
                validation.addFieldError(inputName, errMsg);
            }

            if (LOG.isWarnEnabled()) {
                LOG.warn(errMsg);
            }
        } else if ((!allowedTypesSet.isEmpty()) && (!containsItem(allowedTypesSet, contentType))) {
            String errMsg = getTextMessage(action, "struts.messages.error.content.type.not.allowed", new Object[]{inputName, filename, file.getName(), contentType}, locale);
            if (validation != null) {
                validation.addFieldError(inputName, errMsg);
            }

            if (LOG.isWarnEnabled()) {
                LOG.warn(errMsg);
            }
        } else if ((!allowedExtensionsSet.isEmpty()) && (!hasAllowedExtension(allowedExtensionsSet, filename))) {
            String errMsg = getTextMessage(action, "struts.messages.error.file.extension.not.allowed", new Object[]{inputName, filename, file.getName(), contentType}, locale);
            if (validation != null) {
                validation.addFieldError(inputName, errMsg);
            }

            if (LOG.isWarnEnabled()) {
                LOG.warn(errMsg);
            }
        } else {
            fileIsAcceptable = true;
        }

        return fileIsAcceptable;
    }

    /**
     * @param extensionCollection - Collection of extensions (all lowercase).
     * @param filename - filename to check.
     * @return true if the filename has an allowed extension, false otherwise.
     */
    private boolean hasAllowedExtension(Collection<String> extensionCollection, String filename) {
        if (filename == null) {
            return false;
        }

        String lowercaseFilename = filename.toLowerCase();
        for (String extension : extensionCollection) {
            if (lowercaseFilename.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param itemCollection - Collection of string items (all lowercase).
     * @param item - Item to search for.
     * @return true if itemCollection contains the item, false otherwise.
     */
    private boolean containsItem(Collection<String> itemCollection, String item) {
        for (String pattern : itemCollection) {
            if (matchesWildcard(pattern, item)) {
                return true;
            }
        }
        return false;
    }

    private boolean matchesWildcard(String pattern, String text) {
        Object o = matcher.compilePattern(pattern);
        return matcher.match(new HashMap<String, String>(), text, o);
    }

    private boolean isNonEmpty(Object[] objArray) {
        boolean result = false;
        for (int index = 0; index < objArray.length && !result; index++) {
            if (objArray[index] != null) {
                result = true;
            }
        }
        return result;
    }

    private String getTextMessage(String messageKey, Object[] args, Locale locale) {
        return getTextMessage(null, messageKey, args, locale);
    }

    private String getTextMessage(Object action, String messageKey, Object[] args, Locale locale) {
        if (args == null || args.length == 0) {
            if (action != null && useActionMessageBundle) {
                return LocalizedTextUtil.findText(action.getClass(), messageKey, locale);
            }
            return LocalizedTextUtil.findText(this.getClass(), messageKey, locale);
        } else {
            if (action != null && useActionMessageBundle) {
                return LocalizedTextUtil.findText(action.getClass(), messageKey, locale, DEFAULT_MESSAGE, args);
            }
            return LocalizedTextUtil.findText(this.getClass(), messageKey, locale, DEFAULT_MESSAGE, args);
        }
    }
}
