package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts2.components.Component;

/**
 *
 * @author Administrator
 */
public class BeanWrite extends Component {

    private String name;

    public BeanWrite(ValueStack valueStack) {
        super(valueStack);
    }

    @Override
    public boolean start(Writer writer) {
        try {
            boolean result = super.start(writer);
            String value = (String) this.getStack().findValue(name);
            writer.write(value);
            return result;
        } catch (IOException ex) {
            Logger.getLogger(BeanWrite.class.getName()).log(Level.SEVERE, null, ex);
            return false;
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
