package timey.ioc;

import timey.ioc.ioc.ApplicationContext;
import timey.ioc.ioc.JsonConfiguredApplication;
import timey.ioc.service.GlueTripMaker;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = JsonConfiguredApplication.run("ioc.json");
        GlueTripMaker glueTripMaker = context.getObject(GlueTripMaker.class);
        glueTripMaker.makeATrip();
    }
}
