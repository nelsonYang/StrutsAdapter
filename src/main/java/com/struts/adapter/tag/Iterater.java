package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.annotations.StrutsTag;

/**
 *
 * @author Administrator
 */
@StrutsTag(name = "iterater", tldBodyContent = "empty", tldTagClass = " com.struts.adapter.tag.IteraterTag", description = "iterator a collection")
public class Iterater extends Component {
    public Iterater(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack);
    }

    @Override
    public boolean start(Writer writer) {
        boolean result = super.start(writer);
        return result;
    }
}
