package com.struts.adapter.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.struts.adapter.listener.ApplicationContextListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nelson.yang
 */
public class ExceptionInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        String result;
        try {
            result = invocation.invoke();
        } catch (Exception ex) {
            Logger.getLogger(ApplicationContextListener.class.getName()).log(Level.SEVERE, null, ex);
            result = Action.ERROR;
        }
        return result;
    }
}
