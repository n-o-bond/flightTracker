package com.example.flighttracker.repository;

import com.example.flighttracker.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>{

    @Query(value = "select id, title, airport_of_departure, airport_of_arrival, departure_time, arrived_time, flight_status, price, owner_id from flights where owner_id = ?1 union "+
            "select id, title, airport_of_departure, airport_of_arrival, departure_time, arrived_time, flight_status, price, owner_id from flights inner join flight_passengers on id = flight_id and "+
    "user_id = ?1", nativeQuery = true)
    Set<Flight> getByUserId(long userId);

    @Query(value = "select f from Flight f where f.airportOfDeparture like %?1% and f.airportOfArrival like %?2%")
    List<Flight> search(String departure, String arrival);
}
