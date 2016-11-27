package com.ocean.lmh.base.util;

import java.util.Map;
import com.google.common.collect.ImmutableMap;

public abstract class TypeUtils {
	
    public static final Map<String, String> BaseTypeMap = 
            new ImmutableMap.Builder<String, String>()
            .put("byte", "byte")
            .put("java.lang.Byte", "Byte")
            .put("short", "short")
            .put("java.lang.Short", "Short")
            .put("int", "int" )
            .put("java.lang.Integer", "Integer")
            .put("long", "long")
            .put("java.lang.Long", "Long")
            .put("float", "float")
            .put("java.lang.Float", "Float")
            .put("double", "double")
            .put("java.lang.Double", "Double")
            .put("java.math.BigDecimal", "BigDecimal")
            .put("java.lang.Number", "Number")
            .put("char", "char")
            .put("java.lang.Char", "Char")
            .put("boolean", "boolean")
            .put("java.lang.Boolean", "Boolean")
            .put("java.lang.String", "String")
            .put("java.util.Date", "Date")
            .build();
    
    public static final Map<String, String> NumberTypeMap = 
            new ImmutableMap.Builder<String, String>()
            .put("short", "short")
            .put("java.lang.Short", "Short")
            .put("int", "int" )
            .put("java.lang.Integer", "Integer")
            .put("long", "long")
            .put("java.lang.Long", "Long")
            .put("float", "float")
            .put("java.lang.Float", "Float")
            .put("double", "double")
            .put("java.lang.Double", "Double")
            .put("java.math.BigDecimal", "BigDecimal")
            .put("java.lang.Number", "Number")
            .build();
    
    public static final Map<String, String> BooleanTypeMap = 
            new ImmutableMap.Builder<String, String>()
            .put("boolean", "boolean")
            .put("java.lang.Boolean", "Boolean")
            .build();
    
    public static final Map<String, String> DateTypeMap = 
            new ImmutableMap.Builder<String, String>()
            .put("java.util.Date", "Date")
            .build();
    

    public static boolean isBaseType(String type){
    	return BaseTypeMap.get(type) != null ? true : false;
    }
    
    public static boolean isNumberType(String type){
    	return NumberTypeMap.get(type) != null ? true : false;
    }
    
    public static boolean isBooleanType(String type){
    	return BooleanTypeMap.get(type) != null ? true : false;
    }
    
    public static boolean isDateType(String type){
    	return DateTypeMap.get(type) != null ? true : false;
    }
    
    public static String simpleTypeToStr(Object val){
    	if(val == null ){
    		return null;
    	}
    	return val.toString();
    }
}

