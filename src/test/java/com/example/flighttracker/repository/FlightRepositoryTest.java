package com.example.flighttracker.repository;

import com.example.flighttracker.model.Flight;
import com.example.flighttracker.model.FlightStatus;
import com.example.flighttracker.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@DataJpaTest
@Sql(scripts = {"/schema.sql", "/data.sql"})
public class FlightRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void getByUserIdAndSearchTest(){
        User user = new User();
        user.setId(2L);
        user.setFirstName("Rey");
        user.setLastName("Huan");
        user.setEmail("mar@gmail.com");
        user.setPassword("marPassword8");
        user.setRole(roleRepository.getOne(2L));

        Flight flight = new Flight();
        flight.setId(1L);
        flight.setTitle("DEW12");
        flight.setAirportOfArrival("Gonkong");
        flight.setAirportOfDeparture("Pekin");
        flight.setDepartureTime(LocalDateTime.now().plusHours(2));
        flight.setArrivedTime(LocalDateTime.now().plusHours(26));
        flight.setFlightStatus(FlightStatus.ACTIVE);
        flight.setPrice(new BigDecimal(78478));
        flight.setOwner(user);

        Flight expectedSearch = new Flight();
        expectedSearch.setId(2L);
        expectedSearch.setTitle("IHT12");
        expectedSearch.setAirportOfArrival("Mexico");
        expectedSearch.setAirportOfDeparture("Warsaw");
        expectedSearch.setDepartureTime(LocalDateTime.now());
        expectedSearch.setArrivedTime(LocalDateTime.now().plusHours(5));
        expectedSearch.setFlightStatus(FlightStatus.ACTIVE);
        expectedSearch.setPrice(new BigDecimal(8475));
        expectedSearch.setOwner(user);

        userRepository.save(user);
        flightRepository.save(flight);

        Set<Flight> expected = Collections.singleton(flight);
        Set<Flight> actual = flightRepository.getByUserId(user.getId());

        Assertions.assertEquals(expected,actual);
        Assertions.assertEquals(expected.size(), actual.size());

        flightRepository.save(expectedSearch);

        List<Flight> actualSearch = flightRepository.search("","Mexico");
        Assertions.assertEquals(1, actualSearch.size());
        Assertions.assertEquals(expectedSearch, actualSearch.get(0));
    }
}
