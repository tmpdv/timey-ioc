package timey.ioc.ioc.configurator;

import org.reflections.ReflectionUtils;
import timey.ioc.annotation.InjectBatch;
import timey.ioc.ioc.ApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

public class FieldBatchInjector implements BeanConfigurator {

    @Override
    public void configure(Object bean, String beanName, ApplicationContext context) throws IllegalAccessException {
        for (Field field : ReflectionUtils.getAllFields(bean.getClass())) {
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            if (field.isAnnotationPresent(InjectBatch.class)) {
                if (!Collection.class.isAssignableFrom(fieldType)) {
                    throw new RuntimeException("@InjectBatch annotation is used only for Collection fields");
                }
                ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                Class<?> genericTypeClass = (Class<?>) genericType.getActualTypeArguments()[0];
                Collection<?> injectBatch = context.getBatch(genericTypeClass, (Class<? extends Collection<?>>) fieldType);
                field.set(bean, injectBatch);
            }
            field.setAccessible(false);
        }
    }
}
