package com.struts.adpater.type;


import com.struts.adpater.utils.MethodNameUtils;
import java.lang.reflect.Method;
import org.apache.struts.action.DynaActionForm;

/**
 *
 * @author nelson.yang
 */
public class BoolTypeHandler implements TypeHandler{

    @Override
    public void setValue(Object instance, String propertyName, Object obj) throws Exception {
        String[] booleans = (String[])obj;
        boolean value = Boolean.parseBoolean(booleans[0]);
        String methodName = MethodNameUtils.getSetMethodName(propertyName);
        Method method =  instance.getClass().getMethod(methodName, boolean.class);
        method.invoke(instance, value);
    
    }

    @Override
    public void setDynamicFormValue(DynaActionForm actionForm, String key, Object obj) {
        actionForm.set(key, (Boolean)obj);
    }
  
}
