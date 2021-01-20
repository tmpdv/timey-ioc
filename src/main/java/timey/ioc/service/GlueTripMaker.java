package timey.ioc.service;

import timey.ioc.domain.GlueShop;
import timey.ioc.domain.Monolith;
import timey.ioc.domain.Toxic;
import timey.ioc.ioc.factory.ObjectFactory;

public class GlueTripMaker {

    private final Toxic toxic = ObjectFactory.getInstance().createObject(Toxic.class);
    private final GlueShop shop = ObjectFactory.getInstance().createObject(GlueShop.class);
    private final int tubesNumber = 3;

    public void makeATrip() {
        for (int i = 0; i < 10; i++) {
            shop.addGlue(new Monolith(i));
        }
        toxic.sayHello();
        toxic.buyGlue(tubesNumber, shop);
        for (int i = 0; i < tubesNumber; i++) {
            toxic.sniffGlue();
        }
    }
}
