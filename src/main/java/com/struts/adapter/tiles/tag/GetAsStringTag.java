package com.struts.adapter.tiles.tag;

import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import com.struts.adapter.entity.DefinitionInfo;
import com.struts.adapter.entity.PutInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import org.apache.struts2.components.Component;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.views.jsp.ComponentTagSupport;

/**
 *
 * @author nelson.yang
 */
public class GetAsStringTag extends ComponentTagSupport {

    private String name;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new GetAsString(stack);
    }

    @Override
    public int doStartTag() throws JspException {
        component = getBean(getStack(), (HttpServletRequest) pageContext.getRequest(), (HttpServletResponse) pageContext.getResponse());
        Container container = Dispatcher.getInstance().getContainer();
        container.inject(component);
        populateParams();
        DefinitionInfo definitionInfo = (DefinitionInfo) this.getStack().peek();
        PutInfo value = definitionInfo.getValueByPutTagAttribute(this.name);
        String content =  value.getValue();
        GetAsString getAsString = (GetAsString)component;
        getAsString.setContent(content);
        boolean evalBody = component.start(pageContext.getOut());
        if (evalBody) {
            return component.usesBody() ? EVAL_BODY_BUFFERED : EVAL_BODY_INCLUDE;
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
}
