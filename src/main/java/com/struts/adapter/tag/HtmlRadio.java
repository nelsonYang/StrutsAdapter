package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.UIBean;

/**
 *
 * @author nelson.yang
 */
public class HtmlRadio extends UIBean {
    private static final String templateName = "radio_adapter";
    public HtmlRadio(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack,request,response);
    }

    @Override
    protected String getDefaultTemplate() {
       return templateName;
    }
}
