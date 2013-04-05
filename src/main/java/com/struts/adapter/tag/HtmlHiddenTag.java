package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import com.struts.adpater.utils.MethodNameUtils;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

/**
 * * <html:hidden name="pagedListForm" property="method" /> <input
 * type="hidden" name="method" value=""> <input type="hidden" name="currPage"
 * value="1"> <input type="hidden" name="listName" value="activityCode"> <input
 * type="hidden" name="pageLength" value="10"> @author nelson
 */
public class HtmlHiddenTag extends ComponentTagSupport {

    private String property;
    private String name;
    private String value;
    private Object entity;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        if (name != null && !name.isEmpty()) {
            entity = req.getAttribute(name);
        }
        return new HtmlHidden(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();
        Map parameters = component.getParameters();
        if (value != null) {
            parameters.put("value", value);
        }
        if (entity != null) {
            try {
                Object valueObj = parameters.get("value");
                if (valueObj == null) {
                    Method getMethodNameMethod = entity.getClass().getDeclaredMethod(MethodNameUtils.getGetMethodName(property));
                    Object obj = getMethodNameMethod.invoke(entity);
                    parameters.put("value", obj);
                }
            } catch (Exception ex) {
                Logger.getLogger(HtmlHiddenTag.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        parameters.put("property", property);
        parameters.put("name", name);
        component.getStack().set("parameters", parameters);
    }

    /**
     * @param property the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}
