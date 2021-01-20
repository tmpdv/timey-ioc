package timey.ioc.ioc.config;

public class JsonConfig implements Config {

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> type) {
        return null;
    }
}
