package com.dzytsiuk.dbdeveloper.locator;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static final Map<Class, Object> REGISTRY = new HashMap<>();

    public static <T> void registerService(Class<T> serviceClass, Object service) {
        REGISTRY.put(serviceClass, service);
    }

    public static <T> T get(Class<T> serviceClass) {
        return serviceClass.cast(REGISTRY.get(serviceClass));
    }
}
