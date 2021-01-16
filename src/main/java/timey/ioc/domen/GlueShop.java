package timey.ioc.domen;

import java.util.Set;

public interface GlueShop {

    int getGlueTubesNumber();

    void addGlue(Monolith glue);

    Set<Glue> sellGlue(int number);
}
