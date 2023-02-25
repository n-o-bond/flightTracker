package com.example.flighttracker.service;

import com.example.flighttracker.exception.NullEntityReferenceException;
import com.example.flighttracker.model.Flight;
import com.example.flighttracker.model.FlightStatus;
import com.example.flighttracker.model.User;
import com.example.flighttracker.repository.FlightRepository;
import com.example.flighttracker.service.impl.FlightServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightServiceImpl flightService;

    private Flight flight;
    private User user;


    @BeforeEach
    public void beforeEach(){
        flight = new Flight();
        flight.setId(1L);
        flight.setTitle("FRE15");
        flight.setAirportOfDeparture("Dnipro");
        flight.setAirportOfArrival("Paris");
        flight.setDepartureTime(LocalDateTime.now());
        flight.setArrivedTime(LocalDateTime.now().plusHours(15));
        flight.setFlightStatus(FlightStatus.ACTIVE);
        flight.setPrice(new BigDecimal(16478));
        flight.setOwner(user);

        user = new User();
        user.setId(2L);
    }

    @Test
    public void createValidFlight(){
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);

        Flight actual = flightService.create(flight);
        assertEquals(flight, actual);
        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    public void createFlightIsNull(){
        Exception exception = assertThrows(NullEntityReferenceException.class, ()-> flightService.create(null));
        assertTrue(exception.getMessage().contains("Flight cannot be 'null'"));
        verify(flightRepository, times(0)).save(any(Flight.class));
    }

    @Test
    public void readByIdFlight() {
        when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));

        Flight actual = flightService.readById(flight.getId());
        assertEquals(flight, actual);
        verify(flightRepository).findById(anyLong());
    }

    @Test
    public void readByIdInvalidFlightId() {
        when(flightRepository.findById(anyLong())).thenReturn(Optional.empty());

        long notFoundId = 10L;
        Exception exception = assertThrows(EntityNotFoundException.class, () -> flightService.readById(notFoundId));
        assertTrue(exception.getMessage().contains("Flight with id " + notFoundId + " not found"));
        verify(flightRepository).findById(anyLong());
    }

    @Test
    public void updateExistingFlight(){
        when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);

        assertEquals(flight, flightService.update(flight));

        verify(flightRepository).findById(anyLong());
        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    public void updateNotExistingFlight(){
        assertThrows(EntityNotFoundException.class, ()-> flightService.update(flight));
        verify(flightRepository, times(0)).save(any(Flight.class));
    }

    @Test
    public void updateFlightIsNull(){
        Exception exception = assertThrows(NullEntityReferenceException.class, ()-> flightService.update(null));
        assertTrue(exception.getMessage().contains("Flight cannot be 'null'"));
        verify(flightRepository, times(0)).save(any(Flight.class));
    }

    @Test
    public void deleteExistingFlight(){
        when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));

        flightService.delete(flight.getId());
        verify(flightRepository).findById(anyLong());
    }

    @Test
    public void deleteInvalidFlight(){
        when(flightRepository.findById(anyLong())).thenReturn(Optional.empty());

        long notFoundId = 10L;
        Exception exception = assertThrows(EntityNotFoundException.class, ()->  flightService.delete(notFoundId));
        assertTrue(exception.getMessage().contains("Flight with id " + notFoundId + " not found"));
        verify(flightRepository, times(0)).save(any(Flight.class));
    }

    @Test
    public void getByUserIdEmptySetOfFlights(){
        when(flightRepository.getByUserId(anyLong())).thenReturn(Collections.emptySet());

        var actual = flightService.getByUserId(user.getId());

        assertEquals(0, actual.size());
        verify(flightRepository).getByUserId(anyLong());
    }

    @Test
    public void getByUserIdSetOfFlights() {
        var expected = Set.of(flight, new Flight());

        when(flightRepository.getByUserId(anyLong())).thenReturn(expected);

        var actual = flightService.getByUserId(user.getId());

        verify(flightRepository).getByUserId(anyLong());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAllFlights(){
        var expected = new ArrayList<Flight>();

        when(flightRepository.findAll()).thenReturn(expected);

        var actual = flightService.getAll(null, null);
        assertEquals(actual, expected);

        expected.add(flight);
        actual = flightService.getAll(null, null);
        assertEquals(actual, expected);
        verify(flightRepository, times(2)).findAll();
    }

    @Test
    public void getAllWithSearchParameters(){
        var expected = Collections.singletonList(flight);

        when(flightRepository.search(anyString(),anyString())).thenReturn(expected);

        var actual = flightService.getAll("Dnipro", "");
        assertEquals(actual, expected);

        verify(flightRepository, times(1)).search(anyString(),anyString());
    }
}
