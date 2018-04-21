package com.demo.spring.config;

import java.util.ArrayList;
import java.util.List;

public class Bean {
    private String name;
    private String className;
    private List<Property> properties = new ArrayList<Property>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Bean [name=" + name + ", className=" + className + ", properties=" + properties + "]";
    }
}
