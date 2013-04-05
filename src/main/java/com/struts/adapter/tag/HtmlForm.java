package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.ClosingUIBean;

/**
 * <html:form action="/ToOptionsDetail.do?method=save" method="post"> @author
 * nelson
 */
public class HtmlForm extends ClosingUIBean {

    private static final String form_Open = "form_open_adapter";
    private static final String form_Close = "form_close_adapter";

    public HtmlForm(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return form_Close;
    }

    @Override
    public String getDefaultOpenTemplate() {
        return form_Open;
    }
}
