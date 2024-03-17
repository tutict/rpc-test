package org.tutict.yurpc.spi;

import lombok.extern.slf4j.Slf4j;
import org.tutict.yurpc.serializer.Serializer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.ServiceLoader.load;

@Slf4j
public class SpiLoader {

    /**
     * 储存已经加载的类
     */
    private static Map<String, Map<String, Class<?>>> loaderMap = new ConcurrentHashMap<>();

    private static Map<String, Object> instanceCache = new ConcurrentHashMap<>();

    private static final String RPC_SYSTEM_SPI_DIR = "META-INF/rpc/system/";

    private static final List<Class<?>> LOAD_CLASS_LIST = Arrays.asList(Serializer.class);

    public static void loadAll() {
        log.info("加载所有SPI");
        for (Class<?> aClass : LOAD_CLASS_LIST) {
            load(aClass);
        }
    }

    public static <T> T getInstance(Class<?> tClass, String key) {
        String tClassName = tClass.getName();
        Map<String, Class<?>> classMap = loaderMap.get(tClassName);
        if (classMap == null) {
            throw new RuntimeException(String.format("SpiLoader未加载%s的类型", tClassName));
        }
        if (!classMap.containsKey(key)) {
            throw new RuntimeException(String.format("SpiLoader的%s不存在key=%s的类型", tClassName, key));
        }
        Class<?> implClass= classMap.get(key);
        String implClassName= implClass.getName();

    }
}
