package com.struts.adpater.utils;

/**
 *
 * @author nelson.yang
 */
public class MethodNameUtils {

    private MethodNameUtils() {
    }

    public static String getSetMethodName(String propertyName) {
        char[] properyNameArray = propertyName.toCharArray();
        properyNameArray[0] = (char) (properyNameArray[0] - 32);
        propertyName = new String(properyNameArray);
        String methodName = "set" + propertyName;
        return methodName;
    }
    
     public static String getGetMethodName(String propertyName) {
        char[] properyNameArray = propertyName.toCharArray();
        properyNameArray[0] = (char) (properyNameArray[0] - 32);
        propertyName = new String(properyNameArray);
        String methodName = "get" + propertyName;
        return methodName;
    }
}
