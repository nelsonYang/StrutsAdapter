package com.struts.adapter.tag;

import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import com.struts.adpater.utils.MethodNameUtils;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import org.apache.struts2.components.Component;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.jsp.ComponentTagSupport;

/**
 *
 * @author Administrator
 */
@StrutsTag(name = "logic empty", tldBodyContent = "empty", tldTagClass = " com.struts.adapter.tag.IteraterTag", description = "iterator a collection")
public class LogicEmptyTag extends ComponentTagSupport {

    private String name;
    private String property;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new LogicEmpty(stack, req, res);
    }

    @Override
    public int doStartTag() throws JspException {
        component = getBean(getStack(), (HttpServletRequest) pageContext.getRequest(), (HttpServletResponse) pageContext.getResponse());
        Container container = Dispatcher.getInstance().getContainer();
        container.inject(component);
        populateParams();
        boolean evalBody = component.start(pageContext.getOut());
        boolean isEmpty;
        Object valueObj = this.getStack().findValue(this.name);
        if (valueObj != null) {
            try {
                Method getMethod = valueObj.getClass().getMethod(MethodNameUtils.getGetMethodName(property));
                Object propertyObj = getMethod.invoke(valueObj);
                if (propertyObj != null) {
                    if (propertyObj.toString().isEmpty()) {
                        isEmpty = true;
                    } else {
                        isEmpty = false;
                    }
                } else {
                    isEmpty = false;
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                isEmpty = false;
            }
        } else {
            isEmpty = false;
        }
        if (evalBody) {
            if (isEmpty) {
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
     * @return the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }
}
