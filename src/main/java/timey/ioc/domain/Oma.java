package timey.ioc.domain;

import timey.ioc.ioc.annotation.InjectValue;
import timey.ioc.ioc.annotation.Singleton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Singleton
public class Oma implements GlueShop {

    private final List<Monolith> monolithTubes = new ArrayList<>();

    @InjectValue
    private String advertising;

    @Override
    public int getGlueTubesNumber() {
        return monolithTubes.size();
    }

    @Override
    public void addGlue(Glue glue) {
        if (glue instanceof Monolith) {
            monolithTubes.add((Monolith) glue);
        }
    }

    public Set<Glue> sellGlue(int number) {
        if (number > getGlueTubesNumber()) {
            throw new RuntimeException("There are only " + monolithTubes.size() + " tubes in store");
        }
        Set<Glue> tubesToSale = new HashSet<>();
        for (int i = 0; i < number; i++) {
            Monolith tube = monolithTubes.get(0);
            tubesToSale.add(tube);
            monolithTubes.remove(0);
        }
        System.out.println(advertising);
        return tubesToSale;
    }
}
