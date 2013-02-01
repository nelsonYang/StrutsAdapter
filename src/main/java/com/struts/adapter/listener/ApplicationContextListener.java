package com.struts.adapter.listener;

import com.struts.adapter.message.ApplicationResourceMessage;
import com.struts.adapter.xml.XmlFormParser;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            String filePath = sce.getServletContext().getRealPath("") + "/WEB-INF/struts-config.xml";
            XmlFormParser.parse(filePath);
        } catch (Exception ex) {
            Logger.getLogger(ApplicationContextListener.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("parse struts-config.xml throw an exception", ex);
        }
        //解析消息
        ApplicationResourceMessage.parseApplicationResoures();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
