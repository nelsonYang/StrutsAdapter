package org.apache.struts.action;

/**
 *
 * @author nelson
 */
public class ActionForward {
    private boolean isRedirect;
    private String path;
    private String name;

    public ActionForward(String name) {
        if (name.contains("/") || name.contains("\\.action") || name.contains("\\.do")) {
            this.path = name;
        } else {
            this.name = name;
        }

    }
    
      public ActionForward(String name,boolean isRedirect) {
        if (name.contains("/") || name.contains("\\.action") || name.contains("\\.do")) {
            this.path = name;
        } else {
            this.name = name;
        }
        this.isRedirect = isRedirect;
    }

    public ActionForward() {
        this.name = "";
        this.path = "";
    }

    @Override
    public String toString() {
        if (name != null && !name.isEmpty()) {
            return this.name;
        } else if (path != null && !path.isEmpty()) {
            return this.path;
        } else {
            return "";
        }

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

    public boolean isPath() {
        return path != null && !path.isEmpty();
    }

    /**
     * @return the isRedirect
     */
    public boolean isIsRedirect() {
        return isRedirect;
    }

    /**
     * @param isRedirect the isRedirect to set
     */
    public void setRedirect(boolean isRedirect) {
        this.isRedirect = isRedirect;
    }
}
