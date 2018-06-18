package com.dzytsiuk.dbdeveloper.locator;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static final Map<String, Object> REGISTRY = new HashMap<>();

    public static void registerService(String serviceName, Object service) {
        REGISTRY.put(serviceName, service);
    }

    public static Object get(String serviceName) {
        return REGISTRY.get(serviceName);
    }
}
