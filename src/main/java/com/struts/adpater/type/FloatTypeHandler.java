package com.struts.adpater.type;

import org.apache.struts.action.DynaActionForm;
import com.struts.adpater.utils.MethodNameUtils;
import java.lang.reflect.Method;

/**
 *
 * @author nelson.yang
 */
public class FloatTypeHandler implements TypeHandler {

    @Override
    public void setValue(Object instance, String propertyName, Object obj) throws Exception {
        String[] values = (String[]) obj;
        Float value = Float.parseFloat(values[0]);
        String methodName = MethodNameUtils.getSetMethodName(propertyName);
        Method method = instance.getClass().getMethod(methodName, Float.class);
        method.invoke(instance, value);
    }

    @Override
    public void setDynamicFormValue(DynaActionForm actionForm, String key, Object obj) {
        actionForm.set(key, obj);
    }
}
