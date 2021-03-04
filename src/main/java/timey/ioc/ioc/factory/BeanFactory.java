package timey.ioc.ioc.factory;

import lombok.SneakyThrows;
import timey.ioc.ioc.ApplicationContext;
import timey.ioc.ioc.configurator.BeanConfigurator;
import timey.ioc.ioc.configurator.FieldBeanInjector;
import timey.ioc.ioc.configurator.FieldValueInjector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeanFactory {

    private static final Set<Class<? extends BeanConfigurator>> DEFAULT_BEAN_CONFIGURATOR_CLASSES = Set.of(
            FieldBeanInjector.class, FieldValueInjector.class);

    private final List<BeanConfigurator> configurators = new ArrayList<>();

    private final ApplicationContext context;


    @SneakyThrows
    public BeanFactory(ApplicationContext context) {
        this.context = context;
        // Adding default configurators
        Set<Class<? extends BeanConfigurator>> beanConfiguratorClasses = new HashSet<>(DEFAULT_BEAN_CONFIGURATOR_CLASSES);
        // Adding custom configurators
        beanConfiguratorClasses.addAll(context.getConfig().getScanner().getSubTypesOf(BeanConfigurator.class));

        for (Class<? extends BeanConfigurator> clazz : beanConfiguratorClasses) {
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
