package at.fh.ima.swengs.swengular.model;

import info.movito.themoviedbapi.model.people.PersonCast;

public class Actor
{
    private int id;

    private String name;

    private String character;

    private String profilePath;

    public Actor() {}

    public Actor(PersonCast personCast)
    {
        this.id = personCast.getId();
        this.name = personCast.getName();
        this.character = personCast.getCharacter();
        this.profilePath = personCast.getProfilePath();
    }



    public int getId() { return id; }

    public String getName() { return name; }

    public String getCharacter() { return character; }

    public String getProfilePath() { return profilePath; }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Actor actor = (Actor) o;

        if (id != actor.id) return false;
        return name != null ? name.equals(actor.name) : actor.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", character='" + character + '\'' +
                '}';
    }
}
