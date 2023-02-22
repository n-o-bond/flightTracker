package com.example.flighttracker.dto;

import com.example.flighttracker.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDto {

    long id;
    @Pattern(regexp = "[A-Z0-9]+$",
            message = "Must start with a capital letter followed by one or more digits")
    @NotBlank
    String title;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @NotBlank
    String airportOfDeparture;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @NotBlank
    String airportOfArrival;

    LocalDateTime departureTime;

    LocalDateTime arrivedTime;

    @NotBlank
    String flightStatus;

    BigDecimal price;

    long ownerId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Set<User> passengers;
}
