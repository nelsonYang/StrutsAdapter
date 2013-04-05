package com.struts.adapter.entity;

import com.struts.adapter.constant.PutTagValueTypeEnum;

/**
 *
 * @author nelson.yang
 */
public class PutInfo {
    private String name;
    private String value;
    private PutTagValueTypeEnum type;

    /**
     * @return the name
     */
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
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the type
     */
    public PutTagValueTypeEnum getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(PutTagValueTypeEnum type) {
        this.type = type;
    }
    
}
