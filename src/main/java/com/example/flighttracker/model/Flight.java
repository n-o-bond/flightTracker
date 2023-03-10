package com.example.flighttracker.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "airport_of_departure", nullable = false)
    private String airportOfDeparture;

    @Column(name = "airport_of_arrival", nullable = false)
    private String airportOfArrival;

    @Column(name = "departure_time", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime departureTime;

    @Column(name = "arrived_time", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime arrivedTime;

    @Column(name = "flight_status")
    @Enumerated(EnumType.STRING)
    private FlightStatus flightStatus;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "flight_passengers",
            joinColumns = @JoinColumn(name = "flight_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> passengers = new HashSet<>();

    public void addPassenger(User user){
        passengers.add(user);
        user.getMyFlights().add(this);
    }

    public void removePassenger(User user){
        passengers.remove(user);
        user.getMyFlights().remove(this);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", airport_of_departure='" + airportOfDeparture + '\'' +
                ", airport_of_arrival='" + airportOfArrival + '\'' +
                ", departure_time=" + departureTime +
                ", arrived_time=" + arrivedTime +
                ", flightStatus=" + flightStatus +
                '}';
    }
}
