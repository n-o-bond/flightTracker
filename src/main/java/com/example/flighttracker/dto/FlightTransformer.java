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
        flightDto.setAirport_of_departure(flight.getAirport_of_departure());
        flightDto.setAirport_of_arrival(flight.getAirport_of_arrival());
        flightDto.setDeparture_time(flight.getDeparture_time());
        flightDto.setArrived_time(flight.getArrived_time());
        flightDto.setFlightStatus(flight.getFlightStatus().toString());
        flightDto.setPrice(flight.getPrice());
        flightDto.setOwner_id(flight.getOwner().getId());
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
        flight.setAirport_of_departure(flightDto.getAirport_of_departure());
        flight.setAirport_of_arrival(flightDto.getAirport_of_arrival());
        flight.setDeparture_time(flightDto.getDeparture_time());
        flight.setArrived_time(flightDto.getArrived_time());
        flight.setFlightStatus(FlightStatus.valueOf(flightDto.getFlightStatus()));
        flight.setPrice(flightDto.getPrice());
        flight.setOwner(user);
        flight.setPassengers(flightDto.getPassengers());

        return flight;
    }
}
