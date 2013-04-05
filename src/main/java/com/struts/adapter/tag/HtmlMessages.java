package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.UIBean;
import org.apache.struts2.components.template.Template;
import org.apache.struts2.views.annotations.StrutsTag;

/**
 *
 * @author Administrator
 */
@StrutsTag(name = "htmlactionmessage", tldBodyContent = "empty", tldTagClass = "com.struts.adapter.tag.HtmlMessagesTag", description = "Render action messages if they exists")
public class HtmlMessages extends UIBean {
    private static final String templateName = "actionmessage_adapter";
    public HtmlMessages(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    public boolean start(Writer writer) {
        boolean result = super.start(writer);
        return result;
    }

    @Override
    protected String getDefaultTemplate() {
        return templateName;
    }

    @Override
    public Template buildTemplateName(String myTemplate, String myDefaultTemplate) {
        return super.buildTemplateName(myTemplate, myDefaultTemplate);
    }

    @Override
    public void mergeTemplate(Writer writer, Template template) throws Exception {
        super.mergeTemplate(writer, template);
    }
}
