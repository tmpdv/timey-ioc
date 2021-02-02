package timey.ioc.domain;

import timey.ioc.annotation.Singleton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Singleton
public class Cosmonaut5 implements GlueShop {

    private final List<SuperMonolith> superMonolithTubes = new ArrayList<>();

    @Override
    public int getGlueTubesNumber() {
        return superMonolithTubes.size();
    }

    @Override
    public void addGlue(Glue glue) {
        if (glue instanceof SuperMonolith) {
            superMonolithTubes.add((SuperMonolith) glue);
        }
    }

    @Override
    public Set<Glue> sellGlue(int number) {
        if (number > getGlueTubesNumber()) {
            throw new RuntimeException("There are only " + superMonolithTubes.size() + " tubes in store");
        }
        Set<Glue> tubesToSale = new HashSet<>();
        for (int i = 0; i < number; i++) {
            SuperMonolith tube = superMonolithTubes.get(0);
            tubesToSale.add(tube);
            superMonolithTubes.remove(0);
        }
        return tubesToSale;
    }
}
