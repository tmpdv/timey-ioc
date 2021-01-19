package timey.ioc.ioc;

import org.reflections.Reflections;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;


public class PropertyConfig implements Config {

    private final Properties properties;
    private final Reflections scanner;

    public PropertyConfig(String propertiesFileName) {
        try (InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream(propertiesFileName)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        this.scanner = new Reflections(properties.getProperty("packageToScan"));
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> type) {
        Set<Class<? extends T>> classes = scanner.getSubTypesOf(type);
        if (classes.size() != 1) {
            String className = properties.getProperty("bean." + type.getSimpleName());
            if (className != null) {
                return classes.stream().filter(c -> c.getSimpleName().equals(className)).findAny()
                        .orElseThrow(() -> new RuntimeException("Class " + className + " not found"));
            } else {
                throw new RuntimeException(type + "has 0 or more than 1 implementations");
            }
        }
        return classes.iterator().next();
    }
}
