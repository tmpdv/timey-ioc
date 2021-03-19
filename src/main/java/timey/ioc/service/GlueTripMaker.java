package timey.ioc.service;

import timey.ioc.ioc.annotation.InjectBean;
import timey.ioc.ioc.annotation.Singleton;
import timey.ioc.domain.GlueShop;
import timey.ioc.domain.Monolith;
import timey.ioc.domain.Toxic;

@Singleton
public class GlueTripMaker {

    @InjectBean
    private Toxic toxic;
    @InjectBean("Oma")
    private GlueShop shop;

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
