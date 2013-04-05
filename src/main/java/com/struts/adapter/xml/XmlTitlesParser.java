package com.struts.adapter.xml;

import com.struts.adapter.constant.PutTagValueTypeEnum;
import com.struts.adapter.entity.DefinitionInfo;
import com.struts.adapter.entity.PutInfo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * @author nelson.yang
 */
public class XmlTitlesParser {

    private static final Map<String, DefinitionInfo> definitionInfoMap = new HashMap<String, DefinitionInfo>(64, 1);

    public static void parse(String filePath) throws DocumentException, ClassNotFoundException, FileNotFoundException {
        SAXReader reader = new SAXReader();
        InputStream is = new FileInputStream(filePath);
        Document doc = reader.read(is);
        List<Node> definitionNodes = doc.selectNodes("//definition");
        String definitionName ;
        String path;
        String extend;
        Attribute extendAttribute;
        Attribute pathAttribute;
        Attribute definitionAttribute;
        Element definitionElement;
        DefinitionInfo definitionInfo;
        PutInfo putInfo;
        Element putElement;
        String name;
        String value;
        Attribute nameAttribute;
        Attribute valueAttribute;
        List<Node> putNodes;
        for (Node definitionNode : definitionNodes) {
            definitionName = null;
            path = null;
            extend = null;
            definitionInfo = new DefinitionInfo();
            definitionElement = (Element) definitionNode;
            definitionAttribute = definitionElement.attribute("name");
            if (definitionAttribute != null) {
                definitionName = definitionAttribute.getValue();
            }
            //path or extends choonse one but according to extends we can get path
            pathAttribute = definitionElement.attribute("path");
            if (pathAttribute != null) {
                path = pathAttribute.getValue();
            }
            extendAttribute = definitionElement.attribute("extends");
            if (extendAttribute != null) {
                extend = extendAttribute.getValue();
            }
            definitionInfo.setPath(path);
            definitionInfo.setName(definitionName);
            definitionInfo.setExtend(extend);
            putNodes = definitionNode.selectNodes("./put");
            for (Node putNode : putNodes) {
                name = null;
                value = null;
                putInfo = new PutInfo();
                putElement = (Element) putNode;
                nameAttribute = putElement.attribute("name");
                if (nameAttribute != null) {
                    name = nameAttribute.getValue();
                }
                valueAttribute = putElement.attribute("value");
                if (valueAttribute != null) {
                    value = valueAttribute.getValue();
                }
                putInfo.setName(name);
                putInfo.setValue(value);
                definitionInfo.getPutList().add(putInfo);
            }
            definitionInfoMap.put(definitionName, definitionInfo);
        }
        //set definitioninfo parent value
        setParentDefinition();
        //set Path
        setPathDefinition();
        //set PutValue Tag Type
        setPutTagType();

    }

    private static void setParentDefinition() {
        if (!definitionInfoMap.isEmpty()) {
            Collection<DefinitionInfo> definitionCollection = definitionInfoMap.values();
            String extend;
            DefinitionInfo parent;
            for (DefinitionInfo definitionInfo : definitionCollection) {
                extend = definitionInfo.getExtend();
                if (extend != null && !extend.isEmpty()) {
                    parent = definitionInfoMap.get(extend);
                    if (parent != null) {
                        definitionInfo.setParent(parent);
                    } else {
                        throw new RuntimeException(definitionInfo.getName().concat("cannot find parent ").concat(extend));
                    }
                }
            }
        }
    }

    /**
     * @return the definitionInfoMap
     */
    public static Map<String, DefinitionInfo> getDefinitionInfoMap() {
        return definitionInfoMap;
    }

    private static void setPathDefinition() {
        if (!definitionInfoMap.isEmpty()) {
            Collection<DefinitionInfo> definitionCollection = definitionInfoMap.values();
            String path;
            String extend;
            DefinitionInfo parent;
            for (DefinitionInfo definitionInfo : definitionCollection) {
                path = definitionInfo.getPath();
                extend = definitionInfo.getExtend();
                if (path == null || path.isEmpty()) {
                    if (extend != null && !extend.isEmpty()) {
                        parent = definitionInfoMap.get(extend);
                        if (parent != null) {
                            definitionInfo.setParent(parent);
                            //递归查找path
                            StringBuilder resultValue = new StringBuilder();
                            List<DefinitionInfo> definitionList = new ArrayList<DefinitionInfo>(2);
                            findPathValue(resultValue,definitionInfo.getPath(),definitionInfo);
                            
                            findNoPathDefintion(definitionInfo.getPath(),definitionInfo,definitionList);
                            for (DefinitionInfo definition : definitionList) {
                                definition.setPath(resultValue.toString());
                            }
                        } else {
                            throw new RuntimeException(definitionInfo.getName().concat("cannot find parent ").concat(extend));
                        }
                    }
                }
            }
        }

    }

    private static void findPathValue(StringBuilder resultValue,String path, DefinitionInfo definitionInfo) {
        if (path == null || path.isEmpty()) {
            DefinitionInfo parent = definitionInfo.getParent();
            if (parent != null) {
                String parentPath = parent.getPath();
                if (parentPath != null && !parentPath.isEmpty()) {
                    resultValue.append(parentPath);
                } else {
                   findPathValue(resultValue,parentPath, parent);
                }
            }
        }
    }
    
     private static void findNoPathDefintion(String path, DefinitionInfo definitionInfo, List<DefinitionInfo> definitionList) {
        if (path == null || path.isEmpty()) {
            definitionList.add(definitionInfo);
            DefinitionInfo parent = definitionInfo.getParent();
            if (parent != null) {
                String parentPath = parent.getPath();
                if (parentPath == null || parentPath.isEmpty()) {
                    findNoPathDefintion(parentPath, parent, definitionList);
                }
            }
        }
    }

    private static void setPutTagType() {
        if (!definitionInfoMap.isEmpty()) {
            Collection<DefinitionInfo> definitionCollection = definitionInfoMap.values();
            List<PutInfo> putList;
            String value;
            for (DefinitionInfo definitionInfo : definitionCollection) {
                putList = definitionInfo.getPutList();
                if (!putList.isEmpty()) {
                    for (PutInfo putInfo : putList) {
                        value = putInfo.getValue();
                        value = value.toLowerCase();
                        if (value.contains(".jsp") || value.contains(".html") || value.contains(".xhtml") || value.contains(".xml")) {
                            putInfo.setType(PutTagValueTypeEnum.URL);
                        } else if (definitionInfoMap.get(value) != null) {
                            putInfo.setType(PutTagValueTypeEnum.DEFINITION);
                        } else {
                            putInfo.setType(PutTagValueTypeEnum.STRING);
                        }
                    }
                }
            }
        }

    }
}
