package timey.ioc.ioc.factory;

import timey.ioc.ioc.config.Config;
import timey.ioc.ioc.config.PropertyConfig;

import java.lang.reflect.InvocationTargetException;

public class ObjectFactory {

    private final static ObjectFactory INSTANCE = new ObjectFactory();

    private final Config config;

    public ObjectFactory() {
        config = new PropertyConfig("ioc.properties");
    }

    public static ObjectFactory getInstance() {
        return INSTANCE;
    }

    public <T> T createObject(Class<T> type) {
        Class<? extends T> implClass = type;
        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        try {
            return implClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
