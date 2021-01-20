package timey.ioc;

import timey.ioc.service.GlueTripMaker;

public class App {
    public static void main(String[] args) {
        GlueTripMaker glueTripMaker = new GlueTripMaker();
        glueTripMaker.makeATrip();
    }
}
