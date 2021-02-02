package timey.ioc.ioc;

import timey.ioc.ioc.config.IocConfig;
import timey.ioc.ioc.factory.BeanFactory;
import timey.ioc.ioc.reader.JsonDefinitionReader;


public class JsonConfiguredApplication {

    public static ApplicationContext run(String jsonFile) {
        ApplicationContext context = new ApplicationContext(
                new IocConfig(new JsonDefinitionReader().readDefinition(jsonFile)));

        BeanFactory objectFactory = new BeanFactory(context);
        context.setObjectFactory(objectFactory);

        context.initialize();
        return context;
    }
}
