package com.struts.adapter.tiles.tag;

import com.opensymphony.xwork2.util.ValueStack;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts2.components.Component;

/**
 *
 * @author nelson.yang
 */
public class GetAsString extends Component {

    private String content;

    public GetAsString(ValueStack stack) {
        super(stack);
    }

    @Override
    public boolean start(Writer writer) {
        boolean result = false;
        try {
            result = super.start(writer);
            writer.write(this.content);
            writer.flush();
            return result;
        } catch (IOException ex) {
            Logger.getLogger(GetAsString.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
        return result;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
}
