package com.struts.adapter.xml;

import com.struts.adapter.constant.FormTypeEnum;
import com.struts.adapter.entity.ActionBeanInfo;
import com.struts.adapter.entity.FormBeanInfo;
import com.struts.adapter.entity.FormPropertyInfo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

/**
 *
 * @author Administrator
 */
public class XmlFormParser {

    private static final Map<String, FormBeanInfo> formBeanInfoMap = new HashMap<String, FormBeanInfo>(64, 1);
    private static final Map<String, ActionBeanInfo> actionBeanInfoMap = new HashMap<String, ActionBeanInfo>(128, 1);
  
    public static void parse(String filePath) throws DocumentException, ClassNotFoundException, FileNotFoundException {        SAXReader reader = new SAXReader();
        InputStream is = new FileInputStream(filePath);
        Document doc = reader.read(is);
        List<Node> formBeanNodes = doc.selectNodes("//form-bean");
        String formType;
        String formName;
        String propertyType;
        String propertyName;
        String propertyInital;
        FormBeanInfo formBeanInfo;
        FormPropertyInfo formPropertyInfo;
        Element formBeanElement;
        Element formPropertyElement;
        List<FormPropertyInfo> formPropertyList;
        for (Node formBeanNode : formBeanNodes) {
            formBeanInfo = new FormBeanInfo();
            formBeanElement = (Element) formBeanNode;
            formType = formBeanElement.attribute("type").getValue();
            formName = formBeanElement.attribute("name").getValue();
            formBeanInfo.setName(formName);
            formBeanInfo.setType(formType);
            formPropertyList = new ArrayList<FormPropertyInfo>(0);
            if (formType.contains("DynaActionForm")) {
                formBeanInfo.setFormType(FormTypeEnum.DYNAMIC_FORM);
                //parser form property
                Iterator formPropertyIter = formBeanElement.elementIterator("form-property");
                while (formPropertyIter.hasNext()) {
                    formPropertyElement = (Element) formPropertyIter.next();
                    formPropertyInfo = new FormPropertyInfo();
                    propertyType = formPropertyElement.attribute("type").getValue();
                    propertyName = formPropertyElement.attribute("name").getValue();
                    formPropertyInfo.setName(propertyName);
                    formPropertyInfo.setType(propertyType);
                    Attribute initalAttribute = formPropertyElement.attribute("initial");
                    if (initalAttribute != null) {
                        propertyInital = initalAttribute.getValue();
                        formPropertyInfo.setInitValue(propertyInital);
                    }
                    formPropertyList.add(formPropertyInfo);
                }
                formBeanInfo.setFormPropertyList(formPropertyList);
            } else {
                //parse the field
                formBeanInfo.setFormType(FormTypeEnum.STATIC_FORM);
                Class<?> clazz = Class.forName(formType);
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    formPropertyInfo = new FormPropertyInfo();
                    formPropertyInfo.setName(field.getName());
                    formPropertyInfo.setType(field.getType().getName());
                    formPropertyList.add(formPropertyInfo);
                }
                formBeanInfo.setFormPropertyList(formPropertyList);
            }
            formBeanInfoMap.put(formName, formBeanInfo);
        }
        String actionType;
        String actionName;
        ActionBeanInfo actionBeanInfo;
        Element actionElement;
        List<Node> actionNodes = doc.selectNodes("//action");
        for (Node actionNode : actionNodes) {
            actionElement = (Element) actionNode;
            Attribute actionTypeAttribute = actionElement.attribute("type");
            if (actionTypeAttribute != null) {
                actionBeanInfo = new ActionBeanInfo();
                actionType = actionTypeAttribute.getValue();
                Attribute actionNameAttribute = actionElement.attribute("name");
                if (actionNameAttribute != null) {
                    actionName = actionNameAttribute.getValue();
                    actionBeanInfo.setName(actionName);
                }
                actionBeanInfo.setType(actionType);
                actionBeanInfoMap.put(actionType, actionBeanInfo);
            }
        }
    }

//    public static void main(String[] args) throws ClassNotFoundException {
//        try {
//            parse();
//        } catch (DocumentException ex) {
//            Logger.getLogger(XmlFormParser.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    /**
     * @return the formBeanInfoMap
     */
    public static Map<String, FormBeanInfo> getFormBeanInfoMap() {
        return formBeanInfoMap;
    }

    /**
     * @return the actionBeanInfoMap
     */
    public static Map<String, ActionBeanInfo> getActionBeanInfoMap() {
        return actionBeanInfoMap;
    }
}
