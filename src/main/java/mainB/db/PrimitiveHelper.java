package mainB.db;


import java.util.HashSet;

import java.util.Set;

public class PrimitiveHelper {
    private static final Set<Class<?>> WRAPPER_TYPE_MAP;
    static {
        WRAPPER_TYPE_MAP = new HashSet<>(16);
        WRAPPER_TYPE_MAP.add(int.class);
        WRAPPER_TYPE_MAP.add(Integer.class );
        WRAPPER_TYPE_MAP.add(byte.class) ;
        WRAPPER_TYPE_MAP.add(Byte.class);
        WRAPPER_TYPE_MAP.add(Character.class);
        WRAPPER_TYPE_MAP.add(char.class);
        WRAPPER_TYPE_MAP.add(Boolean.class);
        WRAPPER_TYPE_MAP.add(boolean.class);
        WRAPPER_TYPE_MAP.add(Double.class);
        WRAPPER_TYPE_MAP.add( double.class);
        WRAPPER_TYPE_MAP.add(Float.class);
        WRAPPER_TYPE_MAP.add( float.class);
        WRAPPER_TYPE_MAP.add(Long.class);
        WRAPPER_TYPE_MAP.add( long.class);
        WRAPPER_TYPE_MAP.add(Short.class);
        WRAPPER_TYPE_MAP.add( short.class);
        WRAPPER_TYPE_MAP.add(String.class);
    }
    public static <T>boolean isPrimitiveType(Class<T> source) {
        return WRAPPER_TYPE_MAP.contains(source);
    }
}
