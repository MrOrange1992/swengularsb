package at.fh.ima.swengs.swengular.model;

import javax.persistence.*;

/**
 * Created by jashanica on 4/12/17.
 */

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idGenre;

    private String name;


    @Version
    private long version;

    public Genre() {
    }

    public Genre(long idGenre, String name) {
        this.idGenre = idGenre;
        this.name = name;
    }


    public long getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(long idGenre) {
        this.idGenre = idGenre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
