package com.struts.adapter.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.struts.adapter.constant.Constant;
import java.lang.reflect.Method;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts2.StrutsStatics;

/**
 *
 * @author nelson
 */
public class InvokeInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ActionContext actionContext = invocation.getInvocationContext();
         String method;
        Object invokeMethod = actionContext.getContextMap().get(Constant.INVOKE_METHOD);
        if(invokeMethod != null){
            method = invokeMethod.toString();
        }else{
            method = invocation.getProxy().getMethod();
        }
        final Object action = invocation.getAction();
        Class<?> clazz = action.getClass();
        Map<String, Object> map = actionContext.getContextMap();
        Object requestObj = map.get(StrutsStatics.HTTP_REQUEST);
        Object responseObj = map.get(StrutsStatics.HTTP_RESPONSE);
        ActionForm actionForm = null;
        Object actionFormObj = actionContext.getContextMap().get(Constant.ACTION_FORM);
        if (actionFormObj != null) {
            actionForm = (ActionForm) actionFormObj;
        }
        //执行过程
        HttpServletRequest request = (HttpServletRequest) requestObj;
        HttpServletResponse response = (HttpServletResponse) responseObj;
        Method executeMethod = clazz.getDeclaredMethod(method, ActionMapping.class, ActionForm.class, HttpServletRequest.class, HttpServletResponse.class);
        ActionForward actionForward = (ActionForward) executeMethod.invoke(action, new ActionMapping(), actionForm, request, response);
        if (actionForward != null) {
            if (actionForward.isPath()) {
                response.sendRedirect(actionForward.toString());
                return Action.NONE;
            } else {
                return actionForward.toString();
            }
        } else {
            return Action.NONE;
        }
    }
}
