package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.UIBean;

/**
 * <html:textarea name="OptionsForm" property="systemMessage" rows="4" style="width:250px" styleClass="freetexts"/>
 * @author nelson
 */
public class HtmlTextArea extends UIBean {
    private static final String textArea = "textArea_adapter";

    public HtmlTextArea(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return textArea;
    }
}
