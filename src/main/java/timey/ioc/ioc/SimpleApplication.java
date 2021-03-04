package timey.ioc.ioc;

import timey.ioc.ioc.config.IocConfig;
import timey.ioc.ioc.factory.BeanFactory;
import timey.ioc.ioc.reader.ContextDefinition;

import java.util.Set;

public class SimpleApplication {

    public static ApplicationContext run() {
        ApplicationContext context = new ApplicationContext(new IocConfig(simpleContextDefinition()));
        BeanFactory objectFactory = new BeanFactory(context);

        context.setObjectFactory(objectFactory);
        context.initialize();

        return context;
    }

    private static ContextDefinition simpleContextDefinition() {
        ContextDefinition contextDefinition = new ContextDefinition();
        contextDefinition.setPackageToScan("");
        contextDefinition.setBeans(Set.of());
        return contextDefinition;
    }
}
