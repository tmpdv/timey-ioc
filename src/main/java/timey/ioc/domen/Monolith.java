package timey.ioc.domen;

public class Monolith implements Glue {

    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    public Monolith(Integer id) {
        this.id = id;
    }
}
