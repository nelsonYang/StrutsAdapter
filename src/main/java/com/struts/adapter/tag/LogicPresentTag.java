package com.struts.adapter.tag;

import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import org.apache.struts2.components.Component;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.views.jsp.ComponentTagSupport;

/**
 * <logic:present name="USERINFO" scope="session"> <bean:write name="USERINFO"
 * property="vendorName" scope="session" filter="false" /> </logic:present>
 * <logic:present name="errorFile" scope="request"> @author Administrator
 */
public class LogicPresentTag extends ComponentTagSupport {

    private HttpServletRequest req;
    private String name;
    private String scope;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        this.req = req;
        return new LogicPresent(stack, req, res);
    }

    @Override
    public int doStartTag() throws JspException {
        component = getBean(getStack(), (HttpServletRequest) pageContext.getRequest(), (HttpServletResponse) pageContext.getResponse());
        Container container = Dispatcher.getInstance().getContainer();
        container.inject(component);
        populateParams();
        boolean evalBody = component.start(pageContext.getOut());
        boolean isPresent = false;
        Object value;
        if ("request".equals(this.scope)) {
            value = req.getAttribute(name);
            if (value != null) {
                isPresent = true;
            }
        } else if ("session".equals(this.scope)) {
            HttpSession httpSession = req.getSession();
            if (httpSession != null) {
                value = httpSession.getAttribute(name);
                if (value != null) {
                    isPresent = true;
                }
            }
        } else {
            value = this.getStack().findValue(this.name);
            if (value != null) {
                isPresent = true;
            }
        }
        if (evalBody) {
            if (isPresent) {
                return component.usesBody() ? EVAL_BODY_BUFFERED : EVAL_BODY_INCLUDE;
            } else {
                return SKIP_BODY;
            }
        } else {
            return SKIP_BODY;
        }

    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }
}
