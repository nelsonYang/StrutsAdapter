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
 *<logic:equal name="tableData" property="userEditable" value="1">
					EDIT LOOKUP:
			</logic:equal>			
			<logic:notEqual name="tableData" property="userEditable" value="1">
					VIEW LOOKUP:
			</logic:notEqual>	
 * @author Administrator
 */
@StrutsTag(name = "logic equal", tldBodyContent = "empty", tldTagClass = " com.struts.adapter.tag.LogicEqualTag", description = "justify a value equal another value")
public class LogicEqualTag extends ComponentTagSupport {
    private String name;
    private String property;
    private String value;
    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
       return new LogicEqual(stack,req,res);
    }

      @Override
    public int doStartTag() throws JspException {
        component = getBean(getStack(), (HttpServletRequest) pageContext.getRequest(), (HttpServletResponse) pageContext.getResponse());
        Container container = Dispatcher.getInstance().getContainer();
        container.inject(component);
        populateParams();
        boolean evalBody = component.start(pageContext.getOut());
        boolean isEqual;
        Object valueObj = this.getStack().findValue(this.name);
        if (valueObj != null) {
            try {
                Method getMethod = valueObj.getClass().getMethod(MethodNameUtils.getGetMethodName(property));
                Object propertyObj = getMethod.invoke(valueObj);
                if (propertyObj != null) {
                    if (this.value.equals(String.valueOf(propertyObj))){
                        isEqual = true;
                    } else {
                        isEqual = false;
                    }
                } else {
                    isEqual = false;
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                isEqual = false;
            }
        } else {
            isEqual = false;
        }
        if (evalBody) {
            if (isEqual) {
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

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
}
