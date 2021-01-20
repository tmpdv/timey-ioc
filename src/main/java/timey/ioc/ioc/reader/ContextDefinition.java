package timey.ioc.ioc.reader;

import lombok.Data;

import java.util.Set;

@Data
public class ContextDefinition {
    private String packageToScan;
    private Set<BeanDefinition> beans;
}
