package com.struts.adpater.type;

import org.apache.struts.action.DynaActionForm;

/**
 *
 * @author nelson.yang
 */
public interface TypeHandler{
    public void setValue(Object instance,String propertyName, Object obj) throws Exception;
    public void setDynamicFormValue(DynaActionForm actionForm,String key,Object obj);
}
