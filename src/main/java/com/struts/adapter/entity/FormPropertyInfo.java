package com.struts.adapter.entity;

/**
 *
 * @author Administrator
 */
public class FormPropertyInfo implements PropertyInfo {
    private String name;
    private String type;
    private String initValue;
  

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
     * @return the initValue
     */
    @Override
    public String getInitValue() {
        return initValue;
    }

    /**
     * @param initValue the initValue to set
     */
    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }

 
}
