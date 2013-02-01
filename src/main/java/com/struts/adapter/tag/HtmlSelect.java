package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.ClosingUIBean;

/**
 * <html:select style="width:185px;" property="authorizerId"
 * onmouseover="SelectOnmouseOver(this)" onmouseout="SelectOnmouseOut()"
 * onchange="changeObjEvent()"> <html:options collection="userList"
 * property="value" labelProperty="desc"/> </html:select> @author nelson.yang
 */
public class HtmlSelect extends ClosingUIBean {

    public static final String select_Open = "select_open_adapter";
    public static final String select_Close = "select_close_adapter";

    public HtmlSelect(ValueStack valueStack, HttpServletRequest request, HttpServletResponse response) {
        super(valueStack, request, response);
    }

    @Override
    public String getDefaultOpenTemplate() {
        return select_Open;
    }

    @Override
    protected String getDefaultTemplate() {
        return select_Close;
    }
}