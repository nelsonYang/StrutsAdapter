package com.struts.adapter.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.ParametersInterceptor;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.opensymphony.xwork2.util.reflection.ReflectionContextState;
import com.struts.adapter.constant.Constant;
import com.struts.adapter.entity.ActionBeanInfo;
import com.struts.adapter.entity.FormBeanInfo;
import com.struts.adapter.xml.XmlFormParser;
import com.struts.adpater.form.FormHandler;
import com.struts.adpater.form.FormHandlerFactory;
import java.util.Map;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author nelson.yang
 */
public class ParameterAssignInterceptor extends AbstractInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(ParametersInterceptor.class);

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ActionContext ac = invocation.getInvocationContext();
        Map<String, Object> parameters = ac.getParameters();
        if (parameters != null) {
            Map<String, Object> contextMap = ac.getContextMap();
            try {
                ReflectionContextState.setCreatingNullObjects(contextMap, true);
                ReflectionContextState.setDenyMethodExecution(contextMap, true);
                ReflectionContextState.setReportingConversionErrors(contextMap, true);
                ValueStack stack = ac.getValueStack();
                setParameters(invocation, stack, parameters);
            } finally {
                ReflectionContextState.setCreatingNullObjects(contextMap, false);
                ReflectionContextState.setDenyMethodExecution(contextMap, false);
                ReflectionContextState.setReportingConversionErrors(contextMap, false);
            }
        }
        return invocation.invoke();
    }

    protected void setParameters(ActionInvocation invocation, ValueStack stack, final Map<String, Object> parameters) throws Exception {
        Object action = invocation.getAction();
        Class clazz = action.getClass();
        Map<String, FormBeanInfo> formBeanInfoMap = XmlFormParser.getFormBeanInfoMap();
        Map<String, ActionBeanInfo> actionBeanInfoMap = XmlFormParser.getActionBeanInfoMap();
        ActionBeanInfo actionBeanInfo = actionBeanInfoMap.get(clazz.getName());
        String formName = actionBeanInfo.getName();
        FormBeanInfo formBeanInfo = formBeanInfoMap.get(formName);
        ActionContext actionContext = invocation.getInvocationContext();
        Map<String, Object> paramterMap = actionContext.getParameters();
        ActionForm actionForm = null;
        if (formBeanInfo != null) {
            //开始设置值的过程
         //   List<FormFile> formFileList = (List<FormFile>) actionContext.getContextMap().get(Constant.ACTION_FORM_FILE);
            FormHandler formHandler = FormHandlerFactory.getFormHandler(formBeanInfo.getFormType());
            actionForm = formHandler.handleForm(paramterMap, formBeanInfo);
        }
        invocation.getStack().getRoot().add(0, actionForm);
        actionContext.getContextMap().put(Constant.ACTION_FORM, actionForm);
        //负值method
       Object methodObj =  paramterMap.get("method");
       if(methodObj != null){
           Object[] methods =(Object[])methodObj;
           if(methods.length != 0){
                 actionContext.getContextMap().put(Constant.INVOKE_METHOD, methods[0]);
           }
       }


    }
}
