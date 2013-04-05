package com.struts.adpater.form;

import com.struts.adpater.type.TypeHandler;
import com.struts.adpater.type.TypeHandlerFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;

/**
 *
 * @author Administrator
 */
public class DynamicFormHandler extends AbstractFormHandler {

    @Override
    protected ActionForm newInstance(String type) {
        return new DynaActionForm();
    }

    @Override
    protected void setValue(Object newInstance, Class<?> fieldType, String propertyName, Object values) throws Exception{
       TypeHandler typeHandler ;
        if (newInstance instanceof DynaActionForm) {
           typeHandler  = TypeHandlerFactory.getTypeHandler(fieldType);
            typeHandler.setDynamicFormValue((DynaActionForm) newInstance, propertyName, values);
        } else {
           typeHandler = TypeHandlerFactory.getTypeHandler(fieldType);
           typeHandler.setValue(newInstance, propertyName, values);
        }

    }

    @Override
    protected void initPropertyValue(Object newInstance, String propertyName, Object values) {
        ((DynaActionForm) newInstance).set(propertyName, values);
    }

   
}
