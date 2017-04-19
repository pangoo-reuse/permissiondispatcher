package com.xy.open.util;

/**
 * Created by admin on 2017/4/17.
 */

public class MappingObject {
    static String WRAP__INTEGER = "java.lang.Integer";
    static String WRAP_DOUBLE = "java.lang.Double";
    static String WRAP_FLOAT = "java.lang.Float";
    static String WRAP_LONG = "java.lang.Long";
    static String WRAP_WRAP_SHORT = "java.lang.Short";
    static String WRAP_BYTE = "java.lang.Byte";
    static String WRAP_BOOLEAN = "java.lang.Boolean";
    static String WRAP_CHARACTER = "java.lang.Character";
    static String WRAP_STRING = "java.lang.static String";

    static String INT = "int";
    static String DOUBLE = "double";
    static String LONG = "long";
    static String SHORT = "short";
    static String BYTE = "byte";
    static String BOOLEAN = "boolean";
    static String CHAR = "char";
    static String FLOAT = "float";
    static String STRING = "String";

    public static String getUnpackFiled(String warpField) {
        if (warpField.equals(WRAP__INTEGER)) {
            return INT;
        } else if (warpField.equals(WRAP_DOUBLE)) {
            return DOUBLE;
        } else if (warpField.equals(WRAP_FLOAT)) {
            return FLOAT;
        } else if (warpField.equals(WRAP_LONG)) {
            return LONG;
        } else if (warpField.equals(WRAP_WRAP_SHORT)) {
            return SHORT;
        } else if (warpField.equals(WRAP_BYTE)) {
            return BYTE;
        } else if (warpField.equals(WRAP_BOOLEAN)) {
            return BOOLEAN;
        } else if (warpField.equals(WRAP_CHARACTER)) {
            return CHAR;
        }else if(warpField.equals(WRAP_STRING)){
            return STRING;
        }else return "";


    }

    public static Class<?> getFiled(String filed) {
        if (INT.equals(filed)) {
            return Integer.TYPE;
        } else if (DOUBLE.equals(filed)) {
            return Double.TYPE;
        } else if (LONG.equals(filed)) {
            return Long.TYPE;
        } else if (SHORT.equals(filed)) {
            return Short.TYPE;
        } else if (BYTE.equals(filed)) {
            return Byte.TYPE;
        } else if (BOOLEAN.equals(filed)) {
            return Boolean.TYPE;
        } else if (CHAR.equals(filed)) {
            return Character.TYPE;
        } else if (FLOAT.equals(filed)) {
            return Float.TYPE;
        } else {
            try {
                return Class.forName(filed).getClass();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return Object.class;

    }

}
