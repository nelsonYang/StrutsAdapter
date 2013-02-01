package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

/**
 * <html:textarea name="OptionsForm" property="systemMessage" rows="4"
 * style="width:250px" styleClass="freetexts"/> <textarea name="systemMessage"
 * rows="4" style="width:250px" class="freetexts">test13</textarea>
 *
 * @author nelson.yang
 */
public class HtmlTextAreaTag extends AbstractUITag {

    private String property;
    private int rows;
    private String style;
    private String styleClass;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new HtmlTextArea(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();
        Map parameters = component.getParameters();
        parameters.put("property", property);
        parameters.put("rows", rows);
        parameters.put("style", style);
        parameters.put("styleClass", styleClass);
        component.getStack().set("parameters", parameters);
    }

    /**
     * @param property the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
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
}
