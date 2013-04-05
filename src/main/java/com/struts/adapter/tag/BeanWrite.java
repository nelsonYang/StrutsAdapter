package com.struts.adapter.tag;

import com.opensymphony.xwork2.util.ValueStack;
import com.struts.adpater.utils.MethodNameUtils;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts2.components.Component;

/**
 *
 * @author Administrator
 */
public class BeanWrite extends Component {

    private String name;
    
    private String property;

    public BeanWrite(ValueStack valueStack) {
        super(valueStack);
    }

    @Override
    public boolean start(Writer writer) {
        try {
            boolean result = super.start(writer);
            Object propertyObj;
            String resultValue = "";
            Object valueObj =   this.getStack().findValue(name);
            if(valueObj == null){
               valueObj = this.getStack().getContext().get(name);
            }
            if(valueObj instanceof String){
                    resultValue =  valueObj.toString();
            }else{
                if(valueObj != null){
                    try {
                        Method getMethod =  valueObj.getClass().getMethod(MethodNameUtils.getGetMethodName(property));
                         propertyObj =    getMethod.invoke(valueObj);
                         if(propertyObj  != null){
                             resultValue = propertyObj.toString();
                         }
                    } catch (Exception ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    } 
                }
            
            }
            writer.write(resultValue);
            writer.flush();
            return result;
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
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
