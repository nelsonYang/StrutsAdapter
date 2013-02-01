package com.struts.adapter.message;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nelson.yang
 */
public class ApplicationResourceMessage {

    private static final Properties properties = new Properties();

    public static void parseApplicationResoures() {
        InputStream is = null;
        try {
            is = ApplicationResourceMessage.class.getResourceAsStream("/ApplicationResources.properties");
            properties.load(is);
        } catch (IOException ex) {
            Logger.getLogger(ApplicationResourceMessage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(ApplicationResourceMessage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * @return the properties
     */
    public static String getValue(String key) {
        return properties.getProperty(key);
    }
}
