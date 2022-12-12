package com.example.flighttracker.controller;

import com.example.flighttracker.dto.FlightDto;
import com.example.flighttracker.dto.FlightTransformer;
import com.example.flighttracker.model.Flight;
import com.example.flighttracker.model.FlightStatus;
import com.example.flighttracker.model.User;
import com.example.flighttracker.service.FlightService;
import com.example.flighttracker.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final UserService userService;

    public FlightController(FlightService flightService, UserService userService) {
        this.flightService = flightService;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN') and authentication.details.id == #userId")
    @GetMapping("/create/users/{user_id}")
    public String create(@PathVariable("user_id") long userId, Model model){
        model.addAttribute("flight", new FlightDto());
        model.addAttribute("statuses", FlightStatus.values());
        return "create-flight";
    }

    @PreAuthorize("hasAuthority('ADMIN') and authentication.details.id == #userId")
    @PostMapping("/create/users/{user_id}")
    public String create(@PathVariable("user_id") long userId, Model model,
                         @Validated @ModelAttribute("flight") FlightDto flightDto, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("statuses", FlightStatus.values());
            return "create-flight";
        }

        Flight flight = FlightTransformer.convertToEntity(flightDto, userService.readById(userId));
        flightService.create(flight);
        return "redirect:/flights/all";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/read")
    public String read(@PathVariable long id, Model model){
        FlightDto flightDto = FlightTransformer.convertToDto(flightService.readById(id));
        List<User> passengers = userService.getAll().stream().filter(user -> user.getId() != flightDto.getOwner_id()).collect(Collectors.toList());
        model.addAttribute("flight", flightDto);
        model.addAttribute("passengers", passengers);
        return "flight-info";
    }

    @PreAuthorize("isAuthenticated() and authentication.details.id == #userId")
    @GetMapping("/{id}/update/users/{user_id}")
    public String update(@PathVariable long id,
                         @PathVariable("user_id") long userId, Model model){
        FlightDto flightDto = FlightTransformer.convertToDto(flightService.readById(id));
        model.addAttribute("flight", flightDto);
        model.addAttribute("statuses", FlightStatus.values());
        return "update-flight";
    }

    @PreAuthorize("isAuthenticated() and authentication.details.id == #userId")
    @PostMapping("/{id}/update/users/{user_id}")
    public String update(@PathVariable long id,
                         @PathVariable("user_id") long userId, Model model,
                         @Validated @ModelAttribute("flight") FlightDto flightDto, BindingResult result){
        if(result.hasErrors()){
            flightDto.setOwner_id(userId);
            model.addAttribute("statuses", FlightStatus.values());
            return "update-flight";
        }
        flightDto.setId(id);
        Flight flight = FlightTransformer.convertToEntity(flightDto, userService.readById(userId));
        flightService.update(flight);
        return "redirect:/flights/" + id + "/read";
    }

    @PreAuthorize("hasAuthority('ADMIN') and authentication.details.id == #userId")
    @GetMapping("/{id}/delete/users/{user_id}")
    public String delete(@PathVariable long id,
                         @PathVariable("user_id") long userId){
        flightService.delete(id);
        return "redirect:/";
    }

    @PreAuthorize("isAnonymous() or isAuthenticated()")
    @GetMapping("/all")
    public String getAll(Model model,
                         @Param("departure") String departure,
                         @Param("arrival") String arrival){
        model.addAttribute("flights", flightService.getAll(departure, arrival));

        model.addAttribute("departure", departure);
        model.addAttribute("arrival", arrival);

        return "flights-list";
    }

    @PreAuthorize("isAuthenticated() and authentication.details.id == #userId")
    @GetMapping("/my/users/{user_id}")
    public String getMyFlights(@PathVariable("user_id") long userId, Model model){
        Set<Flight> myFlights = flightService.getByUserId(userId);
        model.addAttribute("myFlights", myFlights);
        model.addAttribute("user", userService.readById(userId));
        return "my-flights";
    }

    @PreAuthorize("isAuthenticated() and authentication.details.id == #userId")
    @GetMapping("/{id}/add/users/{user_id}")
    public String addPassenger(@PathVariable long id,
                               @PathVariable("user_id") long userId){
        Flight flight = flightService.readById(id);
        Set<User> passengers = flight.getPassengers();
        passengers.add(userService.readById(userId));
        flight.setPassengers(passengers);
        flightService.update(flight);
        return "redirect:/flights/" + id + "/read";
    }

    @PreAuthorize("isAuthenticated() and authentication.details.id == #userId")
    @GetMapping("/{id}/remove/users/{user_id}")
    public String removePassengers(@PathVariable long id,
                                   @PathVariable("user_id") long userId){

        Flight flight = flightService.readById(id);
        Set<User> passengers = flight.getPassengers();
        passengers.remove(userService.readById(userId));
        flight.setPassengers(passengers);
        flightService.update(flight);
        return "redirect:/flights/" + id + "/read";
    }

}
