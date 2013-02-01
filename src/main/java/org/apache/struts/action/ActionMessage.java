package org.apache.struts.action;

import com.struts.adapter.message.ApplicationResourceMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 */
public class ActionMessage {
    
    private String key;
    private final List<Object> errorMessageList = new ArrayList<Object>(5);
    protected boolean resource = true;
    
    public ActionMessage(String key) {
        this.key = key;
        String value = ApplicationResourceMessage.getValue(key);
        if (value == null) {
            value = "";
        }
        errorMessageList.add(value);
    }
    
    public ActionMessage(String key, Object errorMessage) {
        this.key = key;
        errorMessageList.add(errorMessage);
    }
    
    public ActionMessage(String key, Object[] errorMessages) {
        this.key = key;
        errorMessageList.addAll(Arrays.asList(errorMessages));
    }
    
    public ActionMessage(String key, Object value0, Object value1) {
        this(key, new Object[]{value0, value1});
    }
    
    public ActionMessage(String key, Object value0, Object value1, Object value2) {
        this(key, new Object[]{value0, value1, value2});
    }
    
    public ActionMessage(String key, Object value0, Object value1, Object value2, Object value3) {
        this(key, new Object[]{value0, value1, value2, value3});
    }
    
    public ActionMessage(String key, boolean resource) {
        this.key = key;
        this.resource = resource;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public boolean isResource() {
        return this.resource;
    }
    
    public List<Object> getErrorMessageList() {
        return errorMessageList;
    }
}
