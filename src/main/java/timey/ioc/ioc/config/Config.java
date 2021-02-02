package timey.ioc.ioc.config;

import org.reflections.Reflections;
import timey.ioc.ioc.reader.BeanDefinition;

import java.util.Set;

public interface Config {
    Reflections getScanner();

    Set<BeanDefinition> getBeanDefinitions();

    <T> Set<Class<? extends T>> getImplClasses(Class<T> type);
}
