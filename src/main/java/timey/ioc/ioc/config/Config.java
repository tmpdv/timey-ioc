package timey.ioc.ioc.config;

public interface Config {
    <T> Class<? extends T> getImplClass(Class<T> type);
}
