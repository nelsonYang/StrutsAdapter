package com.struts.adpater.type;

import org.apache.struts.action.DynaActionForm;
import com.struts.adpater.utils.MethodNameUtils;
import java.lang.reflect.Method;

/**
 *
 * @author nelson.yang
 */
public class FloatPrimitiveArrayTypeHandler implements TypeHandler {

    @Override
    public void setValue(Object instance, String propertyName, Object obj) throws Exception {
        String[] values = (String[]) obj;
        float[] intValues = new float[values.length];
        for (int index = 0; index < values.length; index++) {
            intValues[index] = Float.parseFloat(values[index]);
        }
        String methodName = MethodNameUtils.getSetMethodName(propertyName);
        Method method = instance.getClass().getMethod(methodName, float[].class);
        method.invoke(instance, intValues);
    }

    @Override
    public void setDynamicFormValue(DynaActionForm actionForm, String key, Object obj) {
        actionForm.set(key, obj);
    }
}
