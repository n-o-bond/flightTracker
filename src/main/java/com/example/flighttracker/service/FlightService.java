package com.example.flighttracker.service;

import com.example.flighttracker.model.Flight;

import java.util.List;

public interface FlightService {

    Flight create (Flight flight);
    Flight readById (long id);
    Flight update (Flight flight);
    void delete (long id);
    List<Flight> getByUserId(long userId);
    List<Flight> getAll();
}
