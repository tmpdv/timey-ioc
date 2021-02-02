package timey.ioc.ioc.config;

import lombok.Getter;
import org.reflections.Reflections;
import timey.ioc.ioc.reader.BeanDefinition;
import timey.ioc.ioc.reader.ContextDefinition;

import java.util.Set;


public class IocConfig implements Config {

    @Getter
    private final Reflections scanner;
    @Getter
    private final Set<BeanDefinition> beanDefinitions;

    public IocConfig(ContextDefinition contextDefinition) {
        this.scanner = new Reflections(contextDefinition.getPackageToScan());
        this.beanDefinitions = contextDefinition.getBeans();
    }

    @Override
    public <T> Set<Class<? extends T>> getImplClasses(Class<T> type) {
        return scanner.getSubTypesOf(type);
    }
}
