package com.struts.adapter.entity;

/**
 *
 * @author nelson.yang
 */
public class ResultEntity {
    private String name;
    private  String path;
    private  boolean redirect = false;

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
     * @return the isRedirect
     */
    public boolean getRedirect() {
        return redirect;
    }

    /**
     * @param isRedirect the isRedirect to set
     */
    public void setRedirect(boolean isRedirect) {
        this.redirect = isRedirect;
    }
}
