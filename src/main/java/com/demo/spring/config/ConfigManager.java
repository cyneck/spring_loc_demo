package com.demo.spring.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConfigManager {
    /**
     * 读取配置文件，并返回结果
     *
     * @param path
     * @return
     */
    public static Map<String, Bean> getConfig(String path) throws DocumentException {

        Map<String, Bean> map = new HashMap<String, Bean>();
        //1创建解析器
        SAXReader saxReader = new SAXReader();
        //2加载配置文件
        Document document = saxReader.read(new File(path));
        //3定义xpath表达式去除所有bean元素
        String xpath = "//bean";
        //4对bean元素进行遍历
        List<Element> list = document.selectNodes(xpath);
        if (list != null) {
            for (Element beanFile : list) {
                Bean bean = new Bean();
                //将class,name等属性封装到bean对象中
                String name = beanFile.attributeValue("name");
                String className = beanFile.attributeValue("class");
                bean.setName(name);
                bean.setClassName(className);
                //获得bean元素下的所有property元素，并将其属性封装到property子元素中
                List<Element> children = beanFile.elements("property");
                if (children != null) {
                    for (Element child : children) {
                        Property property = new Property();
                        String pName = child.attributeValue("name");
                        String pValue = child.attributeValue("value");
                        String pRef = child.attributeValue("ref");

                        property.setName(pName);
                        property.setRef(pRef);
                        property.setValue(pValue);
                        //将property对象封装到bean中
                        bean.getProperties().add(property);
                    }
                }
                //将bean对象封装到map中用于返回。
                map.put(name, bean);
            }

        }
        //5返回map
        return map;

    }
}
