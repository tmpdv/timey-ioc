package timey.ioc.ioc.configurator;

import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;
import timey.ioc.ioc.annotation.InjectValue;
import timey.ioc.ioc.ApplicationContext;
import timey.ioc.ioc.reader.BeanDefinition;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FieldValueInjector implements BeanConfigurator {

    @SneakyThrows
    public void configure(Object bean, String beanName, ApplicationContext applicationContext) {
        Class<?> implClass = bean.getClass();

        Set<Field> annotatedFields = ReflectionUtils.getAllFields(implClass).stream()
                .filter(field -> field.isAnnotationPresent(InjectValue.class))
                .collect(Collectors.toSet());
        if (annotatedFields.isEmpty()) {
            return;
        }

        BeanDefinition beanDefinition = applicationContext.getConfig().getBeanDefinitions().stream()
                .filter(b -> beanName.equals(b.getName())).findAny().orElse(null);
        if (beanDefinition == null) {
            return;
        }
        Map<String, String> values = beanDefinition.getValues();
        for (Field field : annotatedFields) {
            String fieldValue = values.get(field.getName());
            if (fieldValue != null) {
                field.setAccessible(true);
                field.set(bean, fieldValue);
                field.setAccessible(false);
            }
        }
    }
}
