package com.example.flighttracker.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "airport_of_departure", nullable = false)
    private String airport_of_departure;

    @Column(name = "airport_of_arrival", nullable = false)
    private String airport_of_arrival;

    @Column(name = "departure_time", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime departure_time;

    @Column(name = "arrived_time", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime arrived_time;

    @Column(name = "flight_status")
    @Enumerated(EnumType.STRING)
    private FlightStatus flightStatus;

    @Column(name = "price", nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(name = "flight_passengers",
            joinColumns = @JoinColumn(name = "flight_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> passengers;

    public Flight() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAirport_of_departure() {
        return airport_of_departure;
    }

    public void setAirport_of_departure(String airport_of_departure) {
        this.airport_of_departure = airport_of_departure;
    }

    public String getAirport_of_arrival() {
        return airport_of_arrival;
    }

    public void setAirport_of_arrival(String airport_of_arrival) {
        this.airport_of_arrival = airport_of_arrival;
    }

    public String getCountries(String country_of_departure, String country_of_arrival){
        return country_of_departure + " - " + country_of_arrival;
    }
    public LocalDateTime getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(LocalDateTime departure_time) {
        this.departure_time = departure_time;
    }

    public LocalDateTime getArrived_time() {
        return arrived_time;
    }

    public void setArrived_time(LocalDateTime arrived_time) {
        this.arrived_time = arrived_time;
    }

    public FlightStatus getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(FlightStatus flightStatus) {
        this.flightStatus = flightStatus;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<User> passengers) {
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", airport_of_departure='" + airport_of_departure + '\'' +
                ", airport_of_arrival='" + airport_of_arrival + '\'' +
                ", departure_time=" + departure_time +
                ", arrived_time=" + arrived_time +
                ", flightStatus=" + flightStatus +
                '}';
    }
}
