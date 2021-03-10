package timey.ioc.ioc.configurator;

import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;
import timey.ioc.annotation.InjectBean;
import timey.ioc.ioc.ApplicationContext;

import java.lang.reflect.Field;

public class FieldBeanInjector implements BeanConfigurator {

    @Override
    @SneakyThrows
    public void configure(Object bean, String beanName, ApplicationContext context) {
        for (Field field : ReflectionUtils.getAllFields(bean.getClass())) {
            if (field.isAnnotationPresent(InjectBean.class)) {
                field.setAccessible(true);

                InjectBean injectBeanAnnotation = field.getAnnotation(InjectBean.class);
                String injectBeanName = injectBeanAnnotation.value();

                Object injectBean;
                if (injectBeanName.equals("")) {
                    injectBean = context.getObject(field.getType());
                } else {
                    injectBean = context.getObject(field.getType(), injectBeanName);
                }

                field.set(bean, injectBean);
                field.setAccessible(false);
            }
        }
    }
}
