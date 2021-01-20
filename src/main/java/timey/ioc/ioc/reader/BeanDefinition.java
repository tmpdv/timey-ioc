package timey.ioc.ioc.reader;

import lombok.Data;

import java.util.Map;

@Data
public class BeanDefinition {
    private String ifc;
    private String impl;
    private String name;
    private Map<String, String> values;
}
