package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

/**
 *
 * @author nelson.yang
 */
public class HtmlSelectTag extends AbstractUITag {

    private String property;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new HtmlSelect(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();
        Map parameters = component.getParameters();
        parameters.put("name", property);
        component.getStack().set("parameters", parameters);
    }

    /**
     * @param property the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }
}
