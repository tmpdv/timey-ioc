package timey.ioc;

import timey.ioc.ioc.reader.ContextDefinition;
import timey.ioc.ioc.reader.JsonDefinitionReader;
import timey.ioc.service.GlueTripMaker;

public class App {
    public static void main(String[] args) {
        GlueTripMaker glueTripMaker = new GlueTripMaker();
        glueTripMaker.makeATrip();
        ContextDefinition contextDefinition = new JsonDefinitionReader().readDefinition("ioc.json");
        System.out.println(contextDefinition.getBeans());
    }
}
