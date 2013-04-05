package org.apache.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import com.struts.adapter.constant.Constant;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author nelson.yang
 */
public class Action extends ActionSupport {

    protected ServletContext getServlet() {
        return ServletActionContext.getServletContext();
    }

    protected ActionForward isAvaliablePrivilege(ActionMapping actionMapping, HttpServletRequest request, HttpServletResponse response, Object obj, String flag) {
        return null;
    }

    public void saveErrors(HttpServletRequest request, ActionMessages actionMessages) {
        request.removeAttribute(Constant.ERROR_KEY);
        request.setAttribute(Constant.ERROR_KEY, actionMessages);
    }

    public void saveMessages(HttpServletRequest request, ActionMessages actionMessages) {
        request.removeAttribute(Constant.ACTION_MESSAGE);
        request.setAttribute(Constant.ACTION_MESSAGE, actionMessages);
    }

    public boolean isTokenValid(HttpServletRequest request, boolean isExist) {
        //struts2 interceptor 已经配置不需要在这里处理
        return true;
    }

    public void saveToken(HttpServletRequest request) {
          //struts2 interceptor 已经配置不需要在这里处理
    }
}
