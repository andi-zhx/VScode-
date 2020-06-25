package com.gen.springbootserver.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtils {

    /**
     * Find the column name of the table by the attribute name of the class（You can
     * use this method when picking one）
     * 
     * @param fileName The Mapper XML file corresponding to the class
     * @param id       id
     *                 <p>
     *                 example： <resultMap id="BaseResultMap" type=
     *                 "com.gen.springbootserver.mybatis.model.User"> this
     *                 id="BaseResultMap"
     *                 </p>
     * @param property Property name (corresponding Java object property name)
     * @return
     * @throws DocumentException
     */
    public static String getMapperColumnByProperty(String fileName, String id, String property)
            throws DocumentException {

        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(XmlUtils.class.getClassLoader().getResourceAsStream(fileName));
        if (document != null) {
            Element root = document.getRootElement();
            if (root != null) {
                @SuppressWarnings("unchecked")
                List<Element> resultMaps = root.elements("resultMap");
                for (Element resultMap : resultMaps) {
                    if (resultMap != null && resultMap.attributeValue("id").equals(id)) {
                        @SuppressWarnings("unchecked")
                        List<Element> properties = resultMap.elements();
                        for (Element prop : properties) {
                            if (prop != null && prop.attributeValue("property").equals(property)) {
                                return prop.attributeValue("column");
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Return the corresponding Element object of the ResultMap (for more than 2
     * times, it is recommended to find the Element object first, and then find the
     * column according to this Element object, which is much more efficient)
     * 
     * @param fileName The Mapper XML file corresponding to the class
     * @param id       id
     *                 <p>
     *                 example： <resultMap id="BaseResultMap" type=
     *                 "com.gen.springbootserver.mybatis.model.User"> this
     *                 id="BaseResultMap"
     *                 </p>
     * @return
     */
    public static Element getResultMapElement(String fileName, String id) {
        try {
            SAXReader saxReader = new SAXReader();
            File configFile = new File("src/main/resources/mapper/" + fileName);
            InputStream in = new FileInputStream(configFile);
            Document document = saxReader.read(in);
            if (document != null) {
                Element root = document.getRootElement();
                if (root != null) {
                    @SuppressWarnings("unchecked")

                    List<Element> resultMaps = root.elements("resultMap");
                    for (Element resultMap : resultMaps) {
                        if (resultMap != null && resultMap.attributeValue("id").equals(id)) {
                            return resultMap;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * When Element finds the Column name of a table based on property (in
     * combination with the method getResultMapElement(), it is many times more
     * efficient to fetch a Column multiple times
     * 
     * @param resultMapElement （ method：getResultMapElement()）
     * @param property         Property name (corresponding Java object property
     *                         name)
     * @return
     */
    public static String getMapperColumnByElement(Element resultMapElement, String property) {
        try {
            if (resultMapElement != null) {
                @SuppressWarnings("unchecked")

                List<Element> properties = resultMapElement.elements();
                for (Element prop : properties) {
                    if (prop != null && prop.attributeValue("property").equals(property)) {
                        return prop.attributeValue("column");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // public static void main( String[] args) {
    // long startTime = new Date().getTime();

    // /*System.out.println(getMapperColumnByProperty("UserMapper.xml","BaseResultMap",
    // "userName"));
    // System.out.println(getMapperColumnByProperty("UserMapper.xml","BaseResultMap",
    // "loginName"));
    // System.out.println(getMapperColumnByProperty("UserMapper.xml","BaseResultMap",
    // "orgName"));
    // System.out.println(getMapperColumnByProperty("UserMapper.xml","BaseResultMap",
    // "sex"));*/

    // Element e = getResultMapElement("UserMapper.xml","BaseResultMap");
    // System.out.println(getMapperColumnByElement(e, "userName"));
    // System.out.println(getMapperColumnByElement(e, "loginName"));
    // System.out.println(getMapperColumnByElement(e, "orgName"));
    // System.out.println(getMapperColumnByElement(e, "sex"));

    // long endTime = new Date().getTime();

    // }

}