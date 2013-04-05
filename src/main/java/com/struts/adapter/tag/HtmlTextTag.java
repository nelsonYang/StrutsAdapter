package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

/**
 * <html:text name="activityCodeForm" property="data.activityId"
 * styleClass="freetexts" onmouseout="changeActivity();"
 * onchange="changeActivity();" style="width:275px" maxlength="4"/> @author
 * nelson.yang
 */
public class HtmlTextTag extends AbstractUITag {

    private String property;
    private String style;
    private String styleClass;
    private String maxlength;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new HtmlText(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();
        Map parameters = component.getParameters();
        parameters.put("property", property);
        parameters.put("style", style);
        parameters.put("styleClass", styleClass);
        parameters.put("maxlength", maxlength);
        component.getStack().set("parameters", parameters);
    }

    /**
     * @param property the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * @param styleClass the styleClass to set
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * @param maxlength the maxlength to set
     */
    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }
}
