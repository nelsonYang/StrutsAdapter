package com.struts.adapter.entity;

import java.util.List;

/**
 *
 * @author Administrator
 */
public interface BeanInfo {

    public String getName();

    public String getType();
    
    public Enum getFormType();

    public List<PropertyInfo> getFormPropertyList();
}
