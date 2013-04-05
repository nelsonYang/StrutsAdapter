package com.struts.adapter.tag;
import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.ListUIBean;

/**
 *<html:options collection="userList" property="value" labelProperty="desc"/>
 * @author nelson.yang
 */
public class HtmlOptions extends ListUIBean {
     private static final String options = "options_adapter";
     public HtmlOptions(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack,request,response);
    }

  
    @Override
    protected String getDefaultTemplate() {
       return options;
    }
}