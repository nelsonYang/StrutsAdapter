package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

/**
 * <html:checkbox name="agreementForm" property="data.approvedSites" onchange="changeClick();" tabindex="34" />  
     <input type="checkbox" name="data.approvedSites" tabindex="34" value="1" onchange="changeClick();">                                    
 * @author nelson.yang
 */
public class HtmlCheckBoxTag extends AbstractUITag {
     private String property;
    
    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new HtmlCheckBox(stack,req,res);
    }
    @Override
    protected void populateParams() {
       super.populateParams();
       Map parameters = component.getParameters();
       parameters.put("property", property);
       component.getStack().set("parameters", parameters);
    }

    /**
     * @param property the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }
    
}
