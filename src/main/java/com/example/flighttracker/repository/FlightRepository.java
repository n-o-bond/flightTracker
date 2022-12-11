package com.example.flighttracker.repository;

import com.example.flighttracker.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>{

    @Query(value = "select id, title, airport_of_departure, airport_of_arrival, departure_time, arrived_time, flight_status, price, owner_id from flights where owner_id = ?1 union "+
            "select id, title, airport_of_departure, airport_of_arrival, departure_time, arrived_time, flight_status, price, owner_id from flights inner join flight_passengers on id = flight_id and "+
    "user_id = ?1", nativeQuery = true)
    List<Flight> getByUserId(long userId);
}
