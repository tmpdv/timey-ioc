package timey.ioc.ioc.configurator;

import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;
import timey.ioc.ioc.annotation.InjectBean;
import timey.ioc.ioc.ApplicationContext;

import java.lang.reflect.Field;

public class FieldBeanInjector implements BeanConfigurator {

    @Override
    @SneakyThrows
    public void configure(Object bean, String beanName, ApplicationContext context) {
        for (Field field : ReflectionUtils.getAllFields(bean.getClass())) {
            if (field.isAnnotationPresent(InjectBean.class)) {
                field.setAccessible(true);

                Class<?> fieldType = field.getType();
                InjectBean injectBeanAnnotation = field.getAnnotation(InjectBean.class);
                String injectBeanName = injectBeanAnnotation.value();

                Object injectBean;
                if (injectBeanName.equals("")) {
                    injectBean = context.getObject(fieldType);
                } else {
                    injectBean = context.getObject(fieldType, injectBeanName);
                }

                field.set(bean, injectBean);
            }
            field.setAccessible(false);
        }
    }
}
