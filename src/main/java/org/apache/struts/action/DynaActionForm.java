package org.apache.struts.action;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nelson.yang
 */
public class DynaActionForm extends ActionForm {

    private Map<String, Object> dynaValues = new HashMap<String, Object>();

    public void set(String name, Object value) {
        this.dynaValues.put(name, value);
    }

    public Object get(String name) {
        return this.dynaValues.get(name);
    }
    public String getString(String name){
        return  (String)this.dynaValues.get(name);
    }
}
