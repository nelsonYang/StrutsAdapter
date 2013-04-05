package com.struts.adapter.xml;

import com.struts.adapter.entity.ActionEntity;
import com.struts.adapter.entity.ResultEntity;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.DocumentException;

/**
 *
 * @author nelson.yang
 */
public class ActionConversion {
    private String fromXmlPath;
    private String toXmlPath;
    public ActionConversion(String fromXmlPath,String toXmlPath){
        this.fromXmlPath = fromXmlPath;
        this.toXmlPath = toXmlPath;
    }
    
    public void convert(){
        if(fromXmlPath == null || fromXmlPath.isEmpty()){
            throw new RuntimeException("need convertion path cannot null or empty !");
        }
        if(toXmlPath == null || toXmlPath.isEmpty()){
            throw new RuntimeException("destination path cannot null or empty !");
        }
        try {
            List<ActionEntity> actionList = XmlFormParser.parseAction(this.fromXmlPath);
            StringBuilder content = new StringBuilder(20*1024);
            content.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> ").append("\n");
            content.append("<!DOCTYPE struts PUBLIC \"-//Apache Software Foundation//DTD Struts Configuration 2.3//EN\" \"http://struts.apache.org/dtds/struts-2.3.dtd\">").append("\n");
            content.append("<struts>").append("\n");
            content.append("<package name=\"struts2\" extends=\"struts-default\">").append("\n");
            List<ResultEntity> resultList;
            for(ActionEntity actionEntity : actionList){
                 content.append("<action name=\"").append(actionEntity.getName()).append("\" class=\"").append(actionEntity.getClazz()).append("\">").append("\n");
                  resultList = actionEntity.getResultList();
                  if(!resultList.isEmpty()){
                      for(ResultEntity resultEntity : resultList){
                            content.append(" <result name=\"").append(resultEntity.getName()).append("\"");
                            if(resultEntity.getRedirect()){
                                content.append(" type=\"redirectAction\"");
                            }
                            content.append(">");
                            content.append(resultEntity.getPath()).append("</result>").append("\n");
                      }
                  }
                 content.append("</action>").append("\n");
            }
           content.append(" </package>").append("\n");
           content.append("</struts>").append("\n");
            File file = new File(this.toXmlPath);
            FileOutputStream fos  =  new FileOutputStream(file);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeUTF(content.toString());
            dos.flush();
            fos.close();
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(ActionConversion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(ActionConversion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ActionConversion.class.getName()).log(Level.SEVERE, null, ex);
        } 
    
    }
    
    
}
