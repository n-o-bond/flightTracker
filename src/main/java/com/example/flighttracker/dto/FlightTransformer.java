package com.example.flighttracker.dto;

import com.example.flighttracker.model.Flight;
import com.example.flighttracker.model.FlightStatus;
import com.example.flighttracker.model.User;
import org.springframework.stereotype.Component;

@Component
public class FlightTransformer {

    public static FlightDto convertToDto(Flight flight){
        if(flight == null){
            return null;
        }

        FlightDto flightDto = new FlightDto();

        flightDto.setId(flight.getId());
        flightDto.setTitle(flight.getTitle());
        flightDto.setAirportOfDeparture(flight.getAirportOfDeparture());
        flightDto.setAirportOfArrival(flight.getAirportOfArrival());
        flightDto.setDepartureTime(flight.getDepartureTime());
        flightDto.setArrivedTime(flight.getArrivedTime());
        flightDto.setFlightStatus(flight.getFlightStatus().toString());
        flightDto.setPrice(flight.getPrice());
        flightDto.setOwnerId(flight.getOwner().getId());
        flightDto.setPassengers(flight.getPassengers());

        return flightDto;
    }

    public static Flight convertToEntity(FlightDto flightDto, User user){
        if(flightDto == null){
            return null;
        }

        Flight flight = new Flight();

        flight.setId(flightDto.getId());
        flight.setTitle(flightDto.getTitle());
        flight.setAirportOfDeparture(flightDto.getAirportOfDeparture());
        flight.setAirportOfArrival(flightDto.getAirportOfArrival());
        flight.setDepartureTime(flightDto.getDepartureTime());
        flight.setArrivedTime(flightDto.getArrivedTime());
        flight.setFlightStatus(FlightStatus.valueOf(flightDto.getFlightStatus()));
        flight.setPrice(flightDto.getPrice());
        flight.setOwner(user);

        return flight;
    }
}
