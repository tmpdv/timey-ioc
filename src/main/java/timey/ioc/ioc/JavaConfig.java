package timey.ioc.ioc;

import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class JavaConfig implements Config {

    private final Reflections scanner;
    private final Map<Class, Class> interfaceToImplMap;

    public JavaConfig(String packageToScan, Map<Class, Class> interfaceToImplMap) {
        this.scanner = new Reflections(packageToScan);
        this.interfaceToImplMap = interfaceToImplMap;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Class<? extends T> getImplClass(Class<T> type) {
        return interfaceToImplMap.computeIfAbsent(type, c -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(type);
            if (classes.size() != 1) {
                throw new RuntimeException(type + "has 0 or more than 1 implementations");
            }
            return classes.iterator().next();
        });
    }
}
