package timey.ioc.ioc.factory;

import timey.ioc.ioc.config.Config;
import timey.ioc.ioc.config.JsonConfig;
import timey.ioc.ioc.reader.BeanDefinition;
import timey.ioc.ioc.reader.ContextDefinition;
import timey.ioc.ioc.reader.JsonDefinitionReader;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ObjectFactory {

    private final static ObjectFactory INSTANCE = new ObjectFactory();
    private final ContextDefinition contextDefinition;

    private final Config config;

    public ObjectFactory() {
        this.contextDefinition = new JsonDefinitionReader().readDefinition("ioc.json");
        this.config = new JsonConfig(contextDefinition);
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
            T object = implClass.getDeclaredConstructor().newInstance();
            BeanDefinition beanDefinition = contextDefinition.getBeans().stream()
                    .filter(b -> b.getIfc().equals(type.getSimpleName())).findAny().orElse(null);
            if (beanDefinition != null) {
                Map<String, String> values = beanDefinition.getValues();
                for (Field field : implClass.getDeclaredFields()) {
                    String fieldValue = values.get(field.getName());
                    if (fieldValue != null) {
                        field.setAccessible(true);
                        field.set(object, fieldValue);
                        field.setAccessible(false);
                    }
                }
            }
            return object;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
