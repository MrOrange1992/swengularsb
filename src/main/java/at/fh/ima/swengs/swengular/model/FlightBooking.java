package at.fh.ima.swengs.swengular.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by johann on 8/23/16.
 */

@Entity

public class FlightBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private FlightSchedule flightSchedule;

    @ManyToOne
    private Passenger passenger;

    @Version
    private long version;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FlightSchedule getFlightSchedule() {
        return flightSchedule;
    }

    public void setFlightSchedule(FlightSchedule flightSchedule) {
        this.flightSchedule = flightSchedule;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
