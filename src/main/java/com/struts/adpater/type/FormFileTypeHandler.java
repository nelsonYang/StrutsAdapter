package com.struts.adpater.type;

import com.struts.adpater.utils.MethodNameUtils;
import java.lang.reflect.Method;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author Administrator
 */
public class FormFileTypeHandler implements TypeHandler {

    @Override
    public void setValue(Object instance, String propertyName, Object obj) throws Exception {
        Object[] objects = (Object[])obj;
        FormFile formFile = (FormFile) objects[0];
        String methodName = MethodNameUtils.getSetMethodName(propertyName);
        Method method = instance.getClass().getMethod(methodName, FormFile.class);
        method.invoke(instance, formFile);
    }

    @Override
    public void setDynamicFormValue(DynaActionForm actionForm, String key, Object obj) {
        actionForm.set(key, obj);
    }
}
