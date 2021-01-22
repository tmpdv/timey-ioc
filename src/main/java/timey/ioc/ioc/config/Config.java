package timey.ioc.ioc.config;

import org.reflections.Reflections;

public interface Config {
    Reflections getScanner();
    <T> Class<? extends T> getImplClass(Class<T> type);
}
