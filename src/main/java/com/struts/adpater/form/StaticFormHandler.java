package com.struts.adpater.form;

import com.struts.adpater.type.TypeHandler;
import com.struts.adpater.type.TypeHandlerFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author Administrator
 */
public class StaticFormHandler extends AbstractFormHandler {

    @Override
    protected ActionForm newInstance(String type) {
        try {
            try {
                return (ActionForm) Class.forName(type).newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(StaticFormHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(StaticFormHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StaticFormHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    protected void setValue(Object newInstance, Class<?> fieldType, String propertyName, Object values)  throws Exception{
        TypeHandler typeHandler = TypeHandlerFactory.getTypeHandler(fieldType);
        typeHandler.setValue(newInstance, propertyName, values);

    }

    @Override
    protected void initPropertyValue(Object newInstance, String propertyName, Object values) {
    }
}
