package com.struts.adapter.entity;

import com.struts.adapter.xml.XmlTitlesParser;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson.yang
 */
public class DefinitionInfo {

    private String name;
    private String path;
    private String extend;
    private DefinitionInfo parent;
    private List<PutInfo> putList = new ArrayList<PutInfo>(0);

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
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the extend
     */
    public String getExtend() {
        return extend;
    }

    /**
     * @param extend the extend to set
     */
    public void setExtend(String extend) {
        this.extend = extend;
    }

    /**
     * @return the parent
     */
    public DefinitionInfo getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(DefinitionInfo parent) {
        this.parent = parent;
    }

    /**
     * @return the putList
     */
    public List<PutInfo> getPutList() {
        return putList;
    }

    /**
     * @param putList the putList to set
     */
    public void setPutList(List<PutInfo> putList) {
        this.putList = putList;
    }

    public PutInfo getValueByPutTagAttribute(String attribute) {
        PutInfo result = null;
        String value = null;
        String putName;
        for (PutInfo putInfo : putList) {
            putName = putInfo.getName();
            if (attribute.equals(putName)) {
                value = putInfo.getValue();
                result = putInfo;
                break;
            }
        }
        if (value == null || value.isEmpty()) {
            this.searchParent(attribute, value,result, extend);
        }
        return result;
    }

    private void searchParent(String attribute,String value,PutInfo result, String extend) {
        if (value == null || value.isEmpty() || result == null) {
            DefinitionInfo definitionInfo = XmlTitlesParser.getDefinitionInfoMap().get(extend);
            if (definitionInfo != null) {
                String putName;
                for (PutInfo putInfo : definitionInfo.getPutList()) {
                    putName = putInfo.getName();
                    if (attribute.equals(putName)) {
                        value = putInfo.getValue();
                        result = putInfo;
                        break;
                    }
                }
                 if (value == null || value.isEmpty()) {
                     this.searchParent(attribute, value,result, definitionInfo.getExtend());
                 }
            }
        }
    }
}
