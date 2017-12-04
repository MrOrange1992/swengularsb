package at.fh.ima.swengs.swengular.model;

import javax.persistence.*;

/**
 * Created by jashanica on 4/12/17.
 */

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;


    @Version
    private long version;

    public Genre() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
