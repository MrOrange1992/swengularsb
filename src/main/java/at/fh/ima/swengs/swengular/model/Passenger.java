package at.fh.ima.swengs.swengular.model;


import at.fh.ima.swengs.swengular.util.JsonDateDeserializer;
import at.fh.ima.swengs.swengular.util.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by johann on 8/23/16.
 */

@Entity
public class Passenger {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private String password;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @Temporal(TemporalType.DATE)
    private Date dayOfBirth;

    private int bonusMiles;

    @OneToMany(mappedBy = "passenger",orphanRemoval = true)
    private List<FlightBooking> bookings;

    @Version
    private long version;


    public Passenger() {
    }

    public Passenger(String firstName, String lastName, Date dayOfBirth, int bonusMiles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dayOfBirth = dayOfBirth;
        this.bonusMiles = bonusMiles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public int getBonusMiles() {
        return bonusMiles;
    }

    public void setBonusMiles(int bonusMiles) {
        this.bonusMiles = bonusMiles;
    }

    public List<FlightBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<FlightBooking> bookings) {
        this.bookings = bookings;
    }
}
