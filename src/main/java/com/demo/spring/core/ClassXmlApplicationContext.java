package com.demo.spring.core;

import com.demo.spring.config.Bean;
import com.demo.spring.config.ConfigManager;
import com.demo.spring.config.Property;
import com.demo.spring.utils.BeanUtils;
import org.dom4j.DocumentException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ClassXmlApplicationContext implements BeanFactory {
    /**
     * 配置信息
     */
    private Map<String, Bean> config;

    /**
     * 用一个map来做spring的容器，放置spring所管理的对象
     */
    private Map<String, Object> context = new HashMap<String, Object>();

    /**
     * 根据bean名称获得bean实例
     *
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName) {
        Object bean = context.get(beanName);
        return bean;
    }

    /**
     * 构造函数,初始化spring容器
     *
     * @param path
     */
    public ClassXmlApplicationContext(String path) throws DocumentException {
        //1读取配置文件获取初始化的bean信息
        config = ConfigManager.getConfig(path);

        //2遍历配置，初始化bean
        if (config != null) {
            for (Map.Entry<String, Bean> en : config.entrySet()) {
                //获取配置中的bean信息
                String beanName = en.getKey();
                Bean bean = en.getValue();

                Object exsitBean = context.get(beanName);
                //因为createBean方法中也会向context中放置bean，我们在初始化的时候先要查看是否已经存在bean
                //如果不存在再创建bean
                if (exsitBean == null) {
                    //根据bean配置创建bean对象
                    Object beanObj = creatBean(bean);
                    //3将初始化好的bean放入容器
                    context.put(beanName, beanObj);
                }

            }
        }


    }

    /**
     * 根据bean配置创建bean对象
     *
     * @param bean
     * @return
     */
    private Object creatBean(Bean bean) {
        // TODO Auto-generated method stub
        //1获得要创建的bean的class
        String className = bean.getClassName();
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("请检查bean的class配置" + className);
        }
        //将class对应的对象创建出来
        Object beanObj = null;
        try {
            beanObj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("bean没有空参构造" + className);
        }

        //2获得bean的属性，将其注入

        if (bean.getProperties() != null) {
            for (Property property : bean.getProperties()) {
                //1:简单value注入
                //获取要注入的属性名称
                String name = property.getName();
                //根据属性名称获得注入属性对应的set方法
                Method setMethod = BeanUtils.getWriteMethod(beanObj, name);
                //创建一个需要注入bean中的属性
                Object parm = null;
                if (property.getValue() != null) {
                    //获取要注入的属性值
                    String value = property.getValue();
                    parm = value;

                }
                //2其他bean的注入
                if (property.getRef() != null) {
                    //先从容器中查找当前要注入的bean是否已经创建并放入容器中
                    Object exsitBean = context.get(property.getRef());
                    if (exsitBean == null) {
                        //如果容器中不存在，则要创建
                        exsitBean = creatBean(config.get(property.getRef()));
                        //将创建好的bean放入容器
                        context.put(property.getRef(), exsitBean);

                    }
                    parm = exsitBean;
                }

                //调用set方法注入
                try {
                    setMethod.invoke(beanObj, parm);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    throw new RuntimeException("bean的属性" + parm + "没有对应的set方法，或者参数不正确" + className);
                }
            }
        }

        return beanObj;
    }
}
