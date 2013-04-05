package com.struts.adpater.form;

import com.struts.adapter.entity.FormBeanInfo;
import com.struts.adapter.entity.PropertyInfo;
import com.struts.adapter.listener.ApplicationContextListener;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author Administrator
 */
public abstract class AbstractFormHandler implements FormHandler {
    

    protected abstract ActionForm newInstance(String beanType);

    protected abstract void setValue(Object newInstance, Class<?> fieldType, String propertyName, Object values) throws Exception;

    protected abstract void initPropertyValue(Object newInstance, String propertyName, Object values);

    @Override
    public ActionForm handleForm(Map<String, Object> paramterMap, FormBeanInfo formBeanInfo) throws Exception {

        Object[] valueObjects;
        Object valueObject;
        Map<String, Object> fieldMap;
        String parameterName;
        String fieldName;
        Field field;
        String formName;
        //分类内部和普通的参数类型
        Map<String, Object> commonParamterMap = new HashMap<String, Object>(8, 1);
        Map<String, Map<String, Object>> innerClassFieldMap = new HashMap<String, Map<String, Object>>(2, 1);
        for (Map.Entry<String, Object> entries : paramterMap.entrySet()) {
            parameterName = entries.getKey();
            valueObject = entries.getValue();
            if (parameterName.contains(".")) {
                //处理内部存在类结构的
                String[] classNamesAndFields = parameterName.split("\\.");
                String className = classNamesAndFields[0];
                String classFieldName = classNamesAndFields[1];
                fieldMap = innerClassFieldMap.get(className);
                if (fieldMap != null) {
                    fieldMap.put(classFieldName, valueObject);
                } else {
                    fieldMap = new HashMap<String, Object>(10, 1);
                    fieldMap.put(classFieldName, valueObject);
                    innerClassFieldMap.put(className, fieldMap);
                }
            } else {
                commonParamterMap.put(parameterName, valueObject);
            }
        }
        //处理普通的参数
        List<PropertyInfo> propertyList = formBeanInfo.getFormPropertyList();
        String propertyInitValue;
        String propertyName;
        String propertyType;
        ActionForm actionForm = this.newInstance(formBeanInfo.getType());
        for (PropertyInfo propertyInfo : propertyList) {
            propertyName = propertyInfo.getName();
            propertyType = propertyInfo.getType();
            propertyInitValue = propertyInfo.getInitValue();
            boolean isExist = false;
            for (Map.Entry<String, Object> entries : commonParamterMap.entrySet()) {
                parameterName = entries.getKey();
                valueObject = entries.getValue();
                if (parameterName.equals(propertyName)) {
                    if (valueObject != null) {
                        valueObjects = (Object[]) valueObject;
                        if (valueObjects != null && valueObjects.length != 0) {
                            this.setValue(actionForm, Class.forName(propertyType), propertyName, valueObject);
                        }
                    }
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                //不存在设置初始化的值
                if (propertyInitValue != null) {
                    this.initPropertyValue(actionForm, propertyName, propertyInitValue);
                }
                //处理子类的内容
                if (!innerClassFieldMap.isEmpty()) {
                    Class<?> className;
                    Class<?> fieldType;
                    for (Map.Entry<String, Map<String, Object>> entries : innerClassFieldMap.entrySet()) {
                        fieldMap = entries.getValue();
                        formName = entries.getKey();
                        if (propertyName.equals(formName)) {
                            className = Class.forName(propertyType);
                            Object innerInstance = className.newInstance();
                            for (Map.Entry<String, Object> innerEntries : fieldMap.entrySet()) {
                                valueObject = innerEntries.getValue();
                                fieldName = innerEntries.getKey();
                                if (valueObject != null) {
                                    valueObjects = (Object[]) valueObject;
                                    if (valueObjects != null && valueObjects.length != 0) {
                                        field = className.getDeclaredField(fieldName);
                                        if (field != null) {
                                            fieldType = field.getType();
                                            this.setValue(innerInstance, fieldType, fieldName, valueObject);
                                        } else {
                                            Logger.getLogger(ApplicationContextListener.class.getName()).log(Level.WARNING, "fieldName:".concat(fieldName).concat(" in the class ").concat(propertyType).concat(" cannot find"));
                                        }
                                    }
                                }
                            }
                            this.setValue(actionForm, Object.class, propertyName, innerInstance);
                        }
                    }
                }
            }
        }
        //设置FormFile的内容
//        String inputName;
//        if (formFileList != null && !formFileList.isEmpty()) {
//            for (FormFile formFile : formFileList) {
//                inputName = formFile.getInputName();
//                this.setValue(actionForm, FormFile.class, inputName, formFile);
//            }
//        }
        return actionForm;
    }
}
