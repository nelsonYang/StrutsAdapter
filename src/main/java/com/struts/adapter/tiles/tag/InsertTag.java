package com.struts.adapter.tiles.tag;

import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import com.struts.adapter.constant.PutTagValueTypeEnum;
import com.struts.adapter.entity.DefinitionInfo;
import com.struts.adapter.entity.PutInfo;
import com.struts.adapter.xml.XmlTitlesParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
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
public class InsertTag extends ComponentTagSupport {

    private String definition;
    private String flush;
    private String attribute;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Insert(stack);
    }

    @Override
    public int doStartTag() throws JspException {
        component = getBean(getStack(), (HttpServletRequest) pageContext.getRequest(), (HttpServletResponse) pageContext.getResponse());
        Container container = Dispatcher.getInstance().getContainer();
        container.inject(component);
        populateParams();
        boolean isFlush = flush == null ? false : "true".equals(flush);
        String relativeURL = "";
        DefinitionInfo definitionInfo;
        if (definition != null && !definition.isEmpty()) {
            definitionInfo = XmlTitlesParser.getDefinitionInfoMap().get(definition);
            if (definitionInfo != null) {
                relativeURL = definitionInfo.getPath();
                this.getStack().push(definitionInfo);
            }
        } else {
            definitionInfo = (DefinitionInfo) this.getStack().peek();
            //get the attribute form Definition tree according to attribute;
            PutInfo value = definitionInfo.getValueByPutTagAttribute(attribute);
            //1.path
            //2.definition
            if (value != null) {
                if (value.getType() == PutTagValueTypeEnum.DEFINITION) {
                    definitionInfo = XmlTitlesParser.getDefinitionInfoMap().get(definition);
                    if (definitionInfo != null) {
                        relativeURL = definitionInfo.getPath();
                        this.getStack().push(definitionInfo);
                    }
                } else if (value.getType() == PutTagValueTypeEnum.URL) {
                    relativeURL = value.getValue();
                }
            } else {
                //error the attribute cannot find
            }
        }
        try {
            pageContext.include(relativeURL, isFlush);
        } catch (ServletException ex) {
            Logger.getLogger(InsertTag.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InsertTag.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean evalBody = component.start(pageContext.getOut());
        if (evalBody) {
            return component.usesBody() ? EVAL_BODY_BUFFERED : EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }

    @Override
    public int doEndTag() throws JspException {
        if(definition != null){
            this.getStack().pop();
        }
        return super.doEndTag();
    }

    /**
     * @return the definition
     */
    public String getDefinition() {
        return definition;
    }

    /**
     * @param definition the definition to set
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    /**
     * @return the flush
     */
    public String getFlush() {
        return flush;
    }

    /**
     * @param flush the flush to set
     */
    public void setFlush(String flush) {
        this.flush = flush;
    }

    /**
     * @return the attribute
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * @param attribute the attribute to set
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
