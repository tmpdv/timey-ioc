package timey.ioc.ioc.config;

import lombok.SneakyThrows;
import timey.ioc.ioc.reader.BeanDefinition;

import java.lang.reflect.Field;
import java.util.Map;

public class InjectFieldValueConfigurator implements ObjectConfigurator {

    @SneakyThrows
    @Override
    public void configure(Object object, BeanDefinition beanDefinition) {
        if (beanDefinition == null) {
            return;
        }
        Class<?> implClass = object.getClass();
        Map<String, String> values = beanDefinition.getValues();
        for (Field field : implClass.getDeclaredFields()) {
            String fieldValue = values.get(field.getName());
            if (fieldValue != null) {
                field.setAccessible(true);
                field.set(object, fieldValue);
                field.setAccessible(false);
            }
        }
    }
}
