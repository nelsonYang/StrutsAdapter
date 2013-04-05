package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

/**
 *
 * @author Administrator
 */
public class BeanWriteTag extends ComponentTagSupport {

    private String name;
    private String property;

    @Override
    public Component getBean(ValueStack vs, HttpServletRequest hsr, HttpServletResponse hsr1) {
        return new BeanWrite(vs);
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

    @Override
    protected void populateParams() {
        super.populateParams();
        BeanWrite beanWriterComponent = (BeanWrite) component;
        beanWriterComponent.setName(name);
        beanWriterComponent.setProperty(property);
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
