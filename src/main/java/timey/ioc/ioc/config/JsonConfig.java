package timey.ioc.ioc.config;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import timey.ioc.annotation.Bean;
import timey.ioc.ioc.reader.ContextDefinition;
import timey.ioc.ioc.reader.JsonDefinitionReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SuppressWarnings("rawtypes")
public class JsonConfig implements Config {

    private final ContextDefinition contextDefinition;
    private final Reflections scanner;
    private final Map<String, Class> classes;
    private final Map<String, Class> interfaces;
    private final Map<Class, Class> interfaceToImplMap = new HashMap<>();

    public JsonConfig(String fileName) {
        this.contextDefinition = new JsonDefinitionReader().readDefinition(fileName);
        this.scanner = new Reflections(contextDefinition.getPackageToScan());

        Set<Class<?>> allTypes = scanner.getTypesAnnotatedWith(Bean.class);

        this.classes = allTypes.stream().filter(c -> !c.isInterface())
                .collect(Collectors.toMap(Class::getSimpleName, c -> c));
        this.interfaces = classes.values().stream().map(Class::getInterfaces)
                .flatMap(Stream::of).distinct()
                .collect(Collectors.toMap(Class::getSimpleName, c -> c));

        contextDefinition.getBeans().forEach(b ->
                interfaceToImplMap.put(interfaces.get(b.getIfc()), classes.get(b.getImpl()))
        );
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
