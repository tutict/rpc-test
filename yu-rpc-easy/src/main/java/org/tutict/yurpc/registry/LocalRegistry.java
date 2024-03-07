package org.tutict.yurpc.registry;

import java.util.HashMap;
import java.util.Map;

public class LocalRegistry {

    private static final Map<String, Class<?>> map = new HashMap<>();
    public static void register(String serviceName, Class<?> implClass) {
        map.put(serviceName, implClass);
    }

    public static Class<?> get(String serviceName) {
        return map.get(serviceName);
    }

    public static void remove(String serviceName) {
        map.remove(serviceName);
    }
}
