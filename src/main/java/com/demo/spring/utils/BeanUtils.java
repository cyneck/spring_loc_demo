package com.demo.spring.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class BeanUtils {
    /**
     * @param beanObj bean对象
     * @param name    要获得bean对象对应的属性名称
     * @return
     */
    public static Method getWriteMethod(Object beanObj, String name) {
        //使用内省实现（基于java反射专门用于操作bean的属性的api）
        Method method = null;
        try {
            //1:分析bean对象-->BeanInfo
            BeanInfo beanInfo = Introspector.getBeanInfo(beanObj.getClass());
            //2:根据BeanInfo获取所有属性的描述器
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            //3：遍历描述器
            if (pds != null) {
                for (PropertyDescriptor pd : pds) {
                    //判断当前属性是否是我们要找的属性
                    String pName = pd.getName();
                    if (pName.equals(name)) {
                        method = pd.getWriteMethod();

                    }
                }
            }


            //4：返回找到的set方法

        } catch (IntrospectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //如果没有找到--》抛出异常，提示用户检查是否创建属性对应的set方法
        if (method == null) {
            throw new RuntimeException("请检查" + name + "属性的set方法是否创建");
        }

        return method;
    }

}
