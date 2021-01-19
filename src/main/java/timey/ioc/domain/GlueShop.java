package timey.ioc.domain;

import java.util.Set;

public interface GlueShop {

    int getGlueTubesNumber();

    void addGlue(Glue glue);

    Set<Glue> sellGlue(int number);
}
