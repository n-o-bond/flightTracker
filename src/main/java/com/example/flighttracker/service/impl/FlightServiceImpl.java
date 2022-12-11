package com.example.flighttracker.service.impl;

import com.example.flighttracker.exception.NullEntityReferenceException;
import com.example.flighttracker.model.Flight;
import com.example.flighttracker.repository.FlightRepository;
import com.example.flighttracker.service.FlightService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight create(Flight flight) {
        if (flight != null){
            return flightRepository.save(flight);
        }
        throw new NullEntityReferenceException("Flight cannot be 'null");
    }

    @Override
    public Flight readById(long id) {
        return flightRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Flight with id " + id + " not found!"));
    }

    @Override
    public Flight update(Flight flight) {
        if(flight != null){
            readById(flight.getId());
            return flightRepository.save(flight);
        }
        throw new NullEntityReferenceException("Flight cannot be 'null");
    }

    @Override
    public void delete(long id) {
        flightRepository.delete(readById(id));
    }

    @Override
    public List<Flight> getByUserId(long userId) {
        List<Flight> flights = flightRepository.getByUserId(userId);
        return flights.isEmpty() ? new ArrayList<>() : flights;
    }

    @Override
    public List<Flight> getAll() {
        List<Flight> flights = flightRepository.findAll();
        return flights.isEmpty() ? new ArrayList<>() : flights;
    }
}
