package com.xy.open.bean;

/**
 * Created by admin on 2017/4/17.
 */

public class ClassValue {
    private Class<?>[] classes;
    private Object[] fields;

    public ClassValue() {
    }

    public ClassValue(Class<?>[] classes, Object[] fileds) {
        this.classes = classes;
        this.fields = fileds;
    }

    public Class<?>[] getClasses() {
        return classes;
    }

    public void setClasses(Class<?>[] classes) {
        this.classes = classes;
    }

    public Object[] getFields() {
        return fields;
    }

    public void setFields(Object[] fields) {
        this.fields = fields;
    }
}
