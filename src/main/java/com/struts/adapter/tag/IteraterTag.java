package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

/*
 * @author Administrator
 */
public class IteraterTag extends ComponentTagSupport {
    private String name;
    private String type;
    private HttpServletRequest httpServletRequest;
    private Iterator<Object> iter;

    @Override
    public Component getBean(ValueStack vs, HttpServletRequest request, HttpServletResponse response) {
        Iterater iteraterComponent = new Iterater(vs, request, response);
        httpServletRequest = request;
        try {
            Collection collection = (Collection) vs.findValue(getName(), Class.forName(type));
            iter = collection.iterator();
            request.setAttribute(this.name, collection);
        } catch (ClassNotFoundException ex) {
            //log it
            Logger.getLogger(IteraterTag.class.getName()).log(Level.SEVERE, null, ex);
        }
        return iteraterComponent;
    }

    @Override
    protected void populateParams() {
        super.populateParams();
        Object result;
        if (iter.hasNext()) {
            Iterater iteraterComponent = (Iterater) component;
            result = iter.next();
            iteraterComponent.getStack().set(id, result);
            httpServletRequest.setAttribute(id, result);
        }
    }

    @Override
    public int doAfterBody() throws JspException {
        Object result;
        if (iter.hasNext()) {
            Iterater iteraterComponent = (Iterater) component;
            result = iter.next();
            iteraterComponent.getStack().set(id, result);
            httpServletRequest.setAttribute(id, result);
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
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
}
