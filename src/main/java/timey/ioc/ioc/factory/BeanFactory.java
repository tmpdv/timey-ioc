package timey.ioc.ioc.factory;

import lombok.SneakyThrows;
import timey.ioc.ioc.ApplicationContext;
import timey.ioc.ioc.configurator.BeanConfigurator;

import java.util.ArrayList;
import java.util.List;

public class BeanFactory {

    private final List<BeanConfigurator> configurators = new ArrayList<>();

    private final ApplicationContext context;

    @SneakyThrows
    public BeanFactory(ApplicationContext context) {
        this.context = context;
        for (Class<? extends BeanConfigurator> clazz :
                context.getConfig().getScanner().getSubTypesOf(BeanConfigurator.class)) {
            this.configurators.add(clazz.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> implClass, String beanName) {
        T object = implClass.getDeclaredConstructor().newInstance();
        for (BeanConfigurator configurator : configurators) {
            configurator.configure(object, beanName, context);
        }
        return object;
    }
}
