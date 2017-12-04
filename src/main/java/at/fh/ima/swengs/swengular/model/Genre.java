package at.fh.ima.swengs.swengular.model;

import javax.persistence.*;

/**
 * Created by jashanica on 4/12/17.
 */

@Entity
public class Genre {

    @Id
    private long id;

    private String name;

    @Version
    private long version;

    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
