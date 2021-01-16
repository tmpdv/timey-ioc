package timey.ioc.service;

import timey.ioc.domen.Evgen;
import timey.ioc.domen.GlueShop;
import timey.ioc.domen.Monolith;
import timey.ioc.domen.Oma;
import timey.ioc.domen.Toxic;

public class GlueTripMaker {

    private final Toxic toxic = new Evgen();
    private final GlueShop shop = new Oma();
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
