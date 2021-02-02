package timey.ioc.ioc;

import lombok.Getter;
import lombok.Setter;
import timey.ioc.annotation.Singleton;
import timey.ioc.ioc.config.Config;
import timey.ioc.ioc.factory.BeanFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    private final Map<Class<?>, Map<String, Object>> beansCache = new ConcurrentHashMap<>();

    @Getter
    private final Config config;

    @Setter
    private BeanFactory objectFactory;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public void initialize() {
        Set<Class<?>> allTypes = config.getScanner().getTypesAnnotatedWith(Singleton.class);
        for (Class<?> type : allTypes) {
            String beanName = getBeanName(type);
            Object object = objectFactory.createObject(type, beanName);
            Map<String, Object> beanMap = beansCache.get(type);
            if (beanMap == null) {
                beansCache.put(type, new HashMap<>(Map.of(object.getClass().getSimpleName(), object)));
            } else {
                beanMap.put(object.getClass().getSimpleName(), object);
            }
        }
    }

    public <T> T getObject(Class<T> type) {
        if (beansCache.containsKey(type)) {
            Map<String, Object> beanMap = beansCache.get(type);
            if (beanMap.size() > 1) {
                throw new RuntimeException(type + " has more than 1 implementations");
            }
            if (beanMap.size() == 1) {
                return (T) beanMap.values().stream().findAny().get();
            }
        }
        Class<? extends T> implClass = type;
        if (type.isInterface()) {
            Set<Class<? extends T>> implClassSet = config.getImplClasses(type);
            if (implClassSet.size() > 1) {
                throw new RuntimeException(type + " has more than 1 implementations");
            }
            implClass = implClassSet.iterator().next();
        }

        T object = objectFactory.createObject(implClass, getBeanName(implClass));
        beansCache.put(type, new HashMap<>(Map.of(object.getClass().getSimpleName(), object)));
        return object;
    }

    public <T> T getObject(Class<T> type, String beanName) {
        if (beansCache.containsKey(type)) {
            Map<String, Object> beanMap = beansCache.get(type);
            if (beanMap.containsKey(beanName)) {
                return (T) beanMap.get(beanName);
            }
        }

        Class<? extends T> implClass = null;
        if (type.isInterface()) {
            Set<Class<? extends T>> implClassSet = config.getImplClasses(type);
            for (Class<? extends T> iClass : implClassSet) {
                if (iClass.isAnnotationPresent(Singleton.class)) {
                    String singletonName = getBeanName(iClass);
                    if (singletonName.equals(beanName)) {
                        implClass = iClass;
                    }
                }
            }
            if (implClass == null) {
                throw new RuntimeException("Bean of type " + type + " is not found");
            }
        } else {
            implClass = type;
        }
        T object = objectFactory.createObject(implClass, beanName);
        beansCache.put(type, new HashMap<>(Map.of(object.getClass().getSimpleName(), object)));
        return object;
    }

    private String getBeanName(Class<?> type) {
        Singleton annotation = type.getAnnotation(Singleton.class);
        if (annotation == null) {
            throw new RuntimeException(type + " is not a singleton");
        }
        String singletonName = annotation.name();
        if (singletonName.equals("")) {
            return type.getSimpleName();
        }
        return singletonName;
    }
}
