package org.example;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

public class MyApplicationContext {

    static ConcurrentHashMap<String, Object> container = new ConcurrentHashMap<>();

    static {
        Class<?>[] classes = new Class[0];
        try {
            classes = ClassUtils.findClasses(
                    new String[]{
                            "org.example"

                    });
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (Class<?> cls : classes) {
            instantiateObject(cls);
        }

    }

    /**
     * instantiate Object and its dependencies
     * @param clazz
     * @return
     */
    private static Object instantiateObject(Class<?> clazz) {
        if (clazz.getAnnotation(MyComponent.class) != null && !container.containsKey(clazz.getName())) {
            try {
                Object object = clazz.newInstance();
                Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.getAnnotation(MyAutowired.class) != null) {
                        System.out.printf("Resolving dependency of %s -- %s\n", clazz.getName(), field.getType().getName());
                        field.setAccessible(true);
                        if (container.containsKey(field.getName())) {
                            field.set(object, container.get(clazz.getName()));
                        } else {
                            field.set(object, instantiateObject(field.getType()));
                        }
                        field.setAccessible(false);
                    }
                }

                container.put(clazz.getName(), object);
                System.out.println("Added object to container: " + clazz.getName());
                return object;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public <T> T getBean(Class<T> requiredType) {
        if (container.containsKey(requiredType.getName())) {
            return (T) container.get(requiredType.getName());
        }

        return null;
    }
}