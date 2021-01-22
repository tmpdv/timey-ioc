package timey.ioc.ioc.factory;

import lombok.SneakyThrows;
import timey.ioc.ioc.config.Config;
import timey.ioc.ioc.config.InjectFieldValueConfigurator;
import timey.ioc.ioc.config.IocConfig;
import timey.ioc.ioc.config.ObjectConfigurator;
import timey.ioc.ioc.reader.BeanDefinition;
import timey.ioc.ioc.reader.ContextDefinition;
import timey.ioc.ioc.reader.JsonDefinitionReader;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {

    private final static ObjectFactory INSTANCE = new ObjectFactory();
    private final ContextDefinition contextDefinition;
    private final List<ObjectConfigurator> configurators = new ArrayList<>();

    private final Config config;


    @SneakyThrows
    public ObjectFactory() {
        this.contextDefinition = new JsonDefinitionReader().readDefinition("ioc.json");
        this.config = new IocConfig(contextDefinition);
        for (Class<? extends ObjectConfigurator> clazz : config.getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            this.configurators.add(clazz.getDeclaredConstructor().newInstance());
        }
    }

    public static ObjectFactory getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        Class<? extends T> implClass = type;
        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
            T object = implClass.getDeclaredConstructor().newInstance();
            BeanDefinition beanDefinition = contextDefinition.getBeans().stream()
                    .filter(b -> b.getIfc().equals(type.getSimpleName())).findAny()
                    .orElse(null);
            for (ObjectConfigurator configurator : configurators) {
                configurator.configure(object, beanDefinition);
            }
            return object;
    }
}
