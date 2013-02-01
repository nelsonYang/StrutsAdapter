package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import com.struts.adapter.constant.Constant;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

/**
 *
 * @author Administrator
 */
public class HtmlMessagesTag extends AbstractUITag {

    private String property;
    private Iterator<Object> iter;

    @Override
    public Component getBean(ValueStack vs, HttpServletRequest request, HttpServletResponse response) {
        ActionMessages actionMessages = (ActionMessages) request.getAttribute(Constant.ACTION_MESSAGE);
        ActionMessages errorMessages = (ActionMessages) request.getAttribute(Constant.ERROR_KEY);
        List<Object> resultList = new ArrayList<Object>(5);
        List<ActionMessage> messageList = new ArrayList<ActionMessage>(0);
        List<ActionMessage> errorMessageList;
        if (property != null && !property.isEmpty()) {
            if (actionMessages != null) {
                messageList = actionMessages.getActionMessageMap().get(property);
                if (messageList == null) {
                    messageList = new ArrayList<ActionMessage>(0);
                }
            }
            if (errorMessages != null) {
                errorMessageList = errorMessages.getActionMessageMap().get(property);
                if (errorMessageList != null) {
                    messageList.addAll(errorMessageList);
                }
            }
        } else {
            if (actionMessages != null) {
                for (List<ActionMessage> actionMessageList : actionMessages.getActionMessageMap().values()) {
                    messageList.addAll(actionMessageList);
                }
            }
            if (errorMessages != null) {
                for (List<ActionMessage> actionMessageList : errorMessages.getActionMessageMap().values()) {
                    messageList.addAll(actionMessageList);
                }
            }
        }
        for (ActionMessage actionMessage : messageList) {
            resultList.addAll(actionMessage.getErrorMessageList());
        }
        Collections.reverse(resultList);
        iter = resultList.iterator();
        HtmlMessages messagesComponent = new HtmlMessages(vs, request, response);
        return messagesComponent;
    }

    @Override
    public int doAfterBody() throws JspException {

        if (iter.hasNext()) {
            HtmlMessages htmlMessageComponent = (HtmlMessages) component;
            htmlMessageComponent.getStack().set(Constant.ACTION_MESSAGE_ITEM, iter.next().toString());
            htmlMessageComponent.addParameter("cssClass", "actionMessage");
            htmlMessageComponent.getStack().set("parameters", htmlMessageComponent.getParameters());
            StringWriter stringWriter = new StringWriter();
            try {
                htmlMessageComponent.mergeTemplate(stringWriter, htmlMessageComponent.buildTemplateName(null, htmlMessageComponent.getDefaultTemplate()));
            } catch (Exception ex) {
                Logger.getLogger(HtmlMessagesTag.class.getName()).log(Level.SEVERE, null, ex);
            }
            htmlMessageComponent.getStack().set(id, stringWriter.toString());
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
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
