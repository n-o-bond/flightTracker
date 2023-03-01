package com.example.flighttracker.controller;

import com.example.flighttracker.dto.FlightDto;
import com.example.flighttracker.model.Flight;
import com.example.flighttracker.model.FlightStatus;
import com.example.flighttracker.service.FlightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlightService flightService;

    @Test
    @WithUserDetails(value = "nick@mail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void createFlightGet() throws Exception {
        long userId = 5L;
        mockMvc.perform(MockMvcRequestBuilders.get("/flights/create/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("flight"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("statuses"))
                .andExpect(MockMvcResultMatchers.view().name("create-flight"));
    }

    @Test
    @Transactional
    @WithUserDetails(value = "nick@mail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void createFlightPost() throws Exception {
        FlightDto flightDto = new FlightDto();
        flightDto.setId(1L);
        flightDto.setTitle("REN45");
        flightDto.setAirportOfArrival("Paris");
        flightDto.setAirportOfDeparture("Beijing");
        flightDto.setDepartureTime(LocalDateTime.now().plusHours(4));
        flightDto.setArrivedTime(LocalDateTime.now().plusHours(18));
        flightDto.setFlightStatus(String.valueOf(FlightStatus.ACTIVE));
        flightDto.setPrice(new BigDecimal(57478));
        flightDto.setOwnerId(5L);

        mockMvc.perform(MockMvcRequestBuilders.post("/flights/create/users/" + flightDto.getOwnerId())
                        .flashAttr("flight", flightDto))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/flights/all"));
    }

    @Test
    @WithUserDetails(value = "nora@mail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void readFlightGet() throws Exception {
        long flightId = 12L;

        mockMvc.perform(MockMvcRequestBuilders.get("/flights/" + flightId + "/read"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("flight"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("passengers"))
                .andExpect(MockMvcResultMatchers.view().name("flight-info"));
    }

    @Test
    @Transactional
    @WithUserDetails(value = "nick@mail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void updateFlightGet() throws Exception{
        long flightId = 12L;
        long userId = 5L;

        mockMvc.perform(MockMvcRequestBuilders.get("/flights/" + flightId + "/update/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("flight"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("statuses"))
                .andExpect(MockMvcResultMatchers.view().name("update-flight"));
    }

    @Test
    @Transactional
    @WithUserDetails(value = "nick@mail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void updateFlightPost() throws Exception {
        FlightDto flightDto = new FlightDto();
        flightDto.setId(16L);
        flightDto.setTitle("REN45");
        flightDto.setAirportOfArrival("Paris");
        flightDto.setAirportOfDeparture("Beijing");
        flightDto.setFlightStatus(String.valueOf(FlightStatus.ACTIVE));
        flightDto.setPrice(new BigDecimal(57478));
        flightDto.setOwnerId(5L);

        mockMvc.perform(MockMvcRequestBuilders.post("/flights/" + flightDto.getId() +"/update/users/" + flightDto.getOwnerId())
                        .flashAttr("flight", flightDto))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/flights/" + flightDto.getId() + "/read"));
    }

    @Test
    @Transactional
    @WithUserDetails(value = "nick@mail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void deleteFlightGet() throws Exception{
        long flightId = 12L;
        long userId = 5L;

        mockMvc.perform(MockMvcRequestBuilders.get("/flights/" + flightId + "/delete/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));
    }

    @Test
    public void getAllFlights() throws Exception {
        List<Flight> expected = flightService.getAll(null,null);

        mockMvc.perform(MockMvcRequestBuilders.get("/flights/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("flights"))
                .andExpect(MockMvcResultMatchers.model().attribute("flights", expected));
    }

    @Test
    @WithUserDetails(value = "nora@mail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void getMyFlights() throws Exception{
        long userId = 6L;
        Set<Flight> expected = flightService.getByUserId(userId);
        mockMvc.perform(MockMvcRequestBuilders.get("/flights/my/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("myFlights"))
                .andExpect(MockMvcResultMatchers.model().attribute("myFlights", expected));
    }

    @Test
    @Transactional
    @WithUserDetails(value = "nora@mail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void addPassenger() throws Exception{
        long flightId = 15L;
        long userId = 6L;

        mockMvc.perform(MockMvcRequestBuilders.get("/flights/" + flightId + "/add/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name( "redirect:/flights/" + flightId + "/read"));;
    }
    @Test
    @Transactional
    @WithUserDetails(value = "nora@mail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void removePassenger() throws Exception{
        long flightId = 15L;
        long userId = 6L;

        mockMvc.perform(MockMvcRequestBuilders.get("/flights/" + flightId + "/remove/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name( "redirect:/flights/" + flightId + "/read"));;
    }
}
