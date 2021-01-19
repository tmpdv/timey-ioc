package timey.ioc.ioc;

public interface Config {
    <T> Class<? extends T> getImplClass(Class<T> type);
}
