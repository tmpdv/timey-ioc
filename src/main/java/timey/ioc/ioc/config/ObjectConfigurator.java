package timey.ioc.ioc.config;

import timey.ioc.ioc.reader.BeanDefinition;

public interface ObjectConfigurator {
    void configure(Object object, BeanDefinition beanDefinition) throws IllegalAccessException;
}
