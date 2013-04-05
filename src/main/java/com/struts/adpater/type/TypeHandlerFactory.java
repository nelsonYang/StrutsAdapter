package com.struts.adpater.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson.yang
 */
public class TypeHandlerFactory {

    private final static Map<Class<?>, TypeHandler> typeHandlerMap = new HashMap<Class<?>, TypeHandler>(10, 1);

    static {
        typeHandlerMap.put(java.lang.String.class, new StringTypeHandler());
        typeHandlerMap.put(java.lang.String[].class, new StringArrayTypeHandler());
        typeHandlerMap.put(java.lang.Integer[].class, new IntegerArrayTypeHandler());
        typeHandlerMap.put(java.lang.Integer.class, new IntegerTypeHandler());
        typeHandlerMap.put(java.lang.Object.class, new ObjectTypeHandler());
        typeHandlerMap.put(java.lang.Boolean.class, new BooleanTypeHandler());
        typeHandlerMap.put(int.class, new IntTypeHandler());
        typeHandlerMap.put(int[].class, new IntArrayTypeHandler());
        typeHandlerMap.put(boolean.class, new BoolTypeHandler());
        typeHandlerMap.put(org.apache.struts.upload.FormFile.class, new FormFileTypeHandler());
        typeHandlerMap.put(org.apache.struts.upload.FormFile[].class, new FormFileArrayTypeHandler());
        typeHandlerMap.put(List.class, new ListTypeHandler());
        typeHandlerMap.put(Map.class, new MapTypeHandler());
        typeHandlerMap.put(double.class, new DoublePrimitiveTypeHandler());
        typeHandlerMap.put(double[].class, new DoublePrimitiveArrayTypeHandler());
        typeHandlerMap.put(float.class, new FloatPrimitiveTypeHandler());
        typeHandlerMap.put(float[].class, new FloatPrimitiveArrayTypeHandler());
        typeHandlerMap.put(long.class, new LongPrimitiveTypeHandler());
        typeHandlerMap.put(long[].class, new LongPrimitiveArrayTypeHandler());
        typeHandlerMap.put(Float.class, new FloatTypeHandler());
        typeHandlerMap.put(Float[].class, new FloatArrayTypeHandler());
        typeHandlerMap.put(Double.class, new DoubleTypeHandler());
        typeHandlerMap.put(Double[].class, new DoubleArrayTypeHandler());
        typeHandlerMap.put(Long.class, new LongTypeHandler());
        typeHandlerMap.put(Long[].class, new LongArrayTypeHandler());
    }

    public static TypeHandler getTypeHandler(Class<?> type) {
        return typeHandlerMap.get(type);
    }
}
