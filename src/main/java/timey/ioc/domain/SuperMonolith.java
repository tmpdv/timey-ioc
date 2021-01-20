package timey.ioc.domain;


public class SuperMonolith implements Glue {

    public SuperMonolith(Integer id) {
        this.id = id;
    }

    private final Integer id;

    @Override
    public Integer getId() {
        return id;
    }
}
