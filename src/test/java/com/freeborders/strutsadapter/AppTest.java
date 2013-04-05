package com.freeborders.strutsadapter;

import com.struts.adapter.entity.DefinitionInfo;
import com.struts.adapter.xml.XmlTitlesParser;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.dom4j.DocumentException;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        try {
            // ActionConversion actionConversion = new ActionConversion("c:/struts-config.xml","c:/struts2.xml");
             //actionConversion.convert();
            XmlTitlesParser.parse("D:/netbeans_project/StrutsSpringHibernate (2)/StrutsSpringHibernate/src/main/webapp/WEB-INF/tiles-defs.xml");
            Map<String,DefinitionInfo> defintionList =   XmlTitlesParser.getDefinitionInfoMap();
            System.out.println("finish...." + defintionList.size());
            for(DefinitionInfo definition : defintionList.values()){
                System.out.println(definition.getName() + "----" + definition.getPath());
            }
        } catch (DocumentException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
