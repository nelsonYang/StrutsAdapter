package com.struts.adapter.listener;

import com.struts.adapter.message.ApplicationResourceMessage;
import com.struts.adapter.xml.XmlFormParser;
import com.struts.adapter.xml.XmlTitlesParser;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
       String filePath;
        try {
             filePath = sce.getServletContext().getRealPath("") + "/WEB-INF/struts-config.xml";
            XmlFormParser.parse(filePath);
        } catch (Exception ex) {
            Logger.getLogger(ApplicationContextListener.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("parse struts-config.xml throw an exception", ex);
        }
       try {
           filePath = sce.getServletContext().getRealPath("") + "/WEB-INF/tiles-defs.xml";
            XmlTitlesParser.parse(filePath);
        } catch (Exception ex) {
            Logger.getLogger(ApplicationContextListener.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("parse tiles-defs.xml throw an exception", ex);
        }
        //解析消息
        ApplicationResourceMessage.parseApplicationResoures();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
