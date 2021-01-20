package timey.ioc.ioc.reader;

import lombok.Data;

import java.util.Set;

@Data
public class ContextDefinition {
    private Set<String> packagesToScan;
    private Set<BeanDefinition> beans;
}
