package timey.ioc.ioc.configurator;

import timey.ioc.ioc.ApplicationContext;

public interface BeanConfigurator {
    void configure(Object bean, String beanName, ApplicationContext context) throws IllegalAccessException;
}
