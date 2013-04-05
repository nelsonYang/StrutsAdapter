package com.struts.adapter.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson.yang
 */
public class ActionEntity {
    private String name;
    private String clazz;
    private List<ResultEntity> resultList = new ArrayList<ResultEntity>(0);

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
     * @return the clazz
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * @param clazz the clazz to set
     */
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    /**
     * @return the resultList
     */
    public List<ResultEntity> getResultList() {
        return resultList;
    }

    /**
     * @param resultList the resultList to set
     */
    public void setResultList(List<ResultEntity> resultList) {
        this.resultList = resultList;
    }
    
}
