package org.apache.struts.action;

/**
 *
 * @author nelson.yang
 */
public class ActionMapping {

   public  ActionForward findForward(String name) {
        return new ActionForward(name);
    }
}
