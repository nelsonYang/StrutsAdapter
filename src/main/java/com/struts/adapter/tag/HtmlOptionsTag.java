package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.components.ListUIBean;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

/**
 * <html:options collection="userList" property="value" labelProperty="desc"/>
 * <option value="163">test jmbm</option> <option value="165">test2
 * jmbm</option> <option value="167">test4 jmbm</option>
 * <html:option value="-1">Select</html:option>
																	<html:option value="1">Month</html:option>
																	<html:option value="2">Quarter</html:option>
																	<html:option value="3">Year</html:option>
 *
 * @author nelson.yang
 */
public class HtmlOptionsTag extends AbstractUITag {
    
    private String labelProperty;
    private String collections;
    private String property;
    private Object list;
    
    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        list = req.getAttribute(this.collections);
        return new HtmlOptions(stack, req, res);
    }
    
    @Override
    protected void populateParams() {
        super.populateParams();
        ListUIBean components = (ListUIBean) component;
        Map parameters = components.getParameters();
        parameters.put("labelProperty", labelProperty);
        parameters.put("options", collections);
        parameters.put("property", property);
        components.setList(list);
      // components.setList(collections);
        components.setListKey(property);
        components.setListValue(labelProperty);
        components.getStack().set("parameters", parameters);
    }

    /**
     * @param labelProperty the labelProperty to set
     */
    public void setLabelProperty(String labelProperty) {
        this.labelProperty = labelProperty;
    }

    /**
     * @param collections the collections to set
     */
    public void setCollections(String collections) {
        this.collections = collections;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(String property) {
        this.property = property;
        
    }
}
