package com.struts.adapter.entity;

import com.struts.adapter.constant.FormTypeEnum;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class FormBeanInfo implements BeanInfo{
    private FormTypeEnum formType;
    private String name;
    private String type;
    private List<FormPropertyInfo> formPropertyList;

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the formPropertyList
     */
    @Override
    public List<PropertyInfo> getFormPropertyList() {
        List<PropertyInfo> propertyList = new ArrayList<PropertyInfo>(formPropertyList.size());
        for(PropertyInfo propertyInfo : formPropertyList){
            propertyList.add(propertyInfo);
        }
        return propertyList;
    }

    /**
     * @param formPropertyList the formPropertyList to set
     */
    public void setFormPropertyList(List<FormPropertyInfo> formPropertyList) {
        this.formPropertyList = formPropertyList;
    }

    /**
     * @return the formType
     */
    @Override
    public FormTypeEnum getFormType() {
        return formType;
    }

    /**
     * @param formType the formType to set
     */
    public void setFormType(FormTypeEnum formType) {
        this.formType = formType;
    }

   
    
}
