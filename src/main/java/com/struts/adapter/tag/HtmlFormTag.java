package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

/**
 *
 * @author nelson
 */
public class HtmlFormTag extends AbstractUITag {

    private String action;
    private String method;
    private String enctype;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new HtmlForm(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();
        Map parameters = component.getParameters();
        parameters.put("name", name);
        parameters.put("action", action);
        parameters.put("method", method);
        parameters.put("enctype", enctype);
        component.getStack().set("parameters", parameters);
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @param enctype the enctype to set
     */
    public void setEnctype(String enctype) {
        this.enctype = enctype;
    }
}
