package timey.ioc.ioc;

import lombok.Getter;
import lombok.Setter;
import timey.ioc.annotation.Singleton;
import timey.ioc.ioc.config.Config;
import timey.ioc.ioc.factory.BeanFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationContext {

    private final Map<String, Object> beansCache = new ConcurrentHashMap<>();

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
            beansCache.put(beanName, object);
        }
    }

    public Collection<?> getBatch(Class<?> type, Class<? extends Collection<?>> collectionType) {
        Stream<?> batchStream = config.getImplClasses(type).stream().map(this::getObject);
        if (collectionType.equals(Set.class)) {
            return batchStream.collect(Collectors.toSet());
        } else if (collectionType.equals(List.class)) {
            return batchStream.collect(Collectors.toList());
        } else {
            throw new RuntimeException("Collection type " + type.getSimpleName() + " is not supported");
        }
    }

    public <T> T getObject(Class<T> type) {
        if (type.isAnnotationPresent(Singleton.class)) {
            return (T) beansCache.get(getBeanName(type));
        }

        List<Object> beanList = beansCache.values().stream()
                .filter(o -> type.isAssignableFrom(o.getClass()))
                .collect(Collectors.toList());
        if (beanList.size() > 1) {
            throw new RuntimeException(type + " has more than 1 implementations");
        }
        if (beanList.size() == 1) {
            return (T) beanList.get(0);
        }

        Class<? extends T> implClass = type;
        if (type.isInterface()) {
            Set<Class<? extends T>> implClassSet = config.getImplClasses(type);
            if (implClassSet.size() > 1) {
                throw new RuntimeException(type + " has more than 1 implementations");
            }
            implClass = implClassSet.iterator().next();
        }

        String beanName = getBeanName(implClass);
        T object = objectFactory.createObject(implClass, getBeanName(implClass));

        beansCache.put(beanName, object);
        return object;
    }

    public <T> T getObject(Class<T> type, String beanName) {
        T object = (T) beansCache.get(beanName);
        if (object != null) {
            if (type.isAssignableFrom(object.getClass())) {
                return (T) object;
            } else {
                throw new RuntimeException("Bean with name " + beanName + " is not of type " + type);
            }
        } else {
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
            object = objectFactory.createObject(implClass, beanName);
            beansCache.put(beanName, object);
            return object;
        }
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
