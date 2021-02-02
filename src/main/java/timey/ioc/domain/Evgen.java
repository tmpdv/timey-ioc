package timey.ioc.domain;

import timey.ioc.annotation.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class Evgen implements Toxic {

    private final List<Glue> glueTubes = new ArrayList<>();

    @Override
    public void sayHello() {
        System.out.println("Hello! My name is Evgen and I'm gonna sniff some sweet wonderful glue!");
    }

    @Override
    public void buyGlue(int number, GlueShop shop) {
        int glueTubesInStore = shop.getGlueTubesNumber();
        if (number <= glueTubesInStore) {
            glueTubes.addAll(shop.sellGlue(number));
        } else {
            glueTubes.addAll(shop.sellGlue(glueTubesInStore));
        }

    }

    @Override
    public void sniffGlue() {
        if (glueTubes.size() == 0) {
            throw new RuntimeException("Nothing to sniff");
        }
        System.out.println("Evgen is sniffing glue. So good...");
        glueTubes.remove(0);
    }
}
