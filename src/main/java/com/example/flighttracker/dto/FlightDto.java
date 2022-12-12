package com.example.flighttracker.dto;

import com.example.flighttracker.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
    String airport_of_departure;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @NotBlank
    String airport_of_arrival;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    LocalDateTime departure_time;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    LocalDateTime arrived_time;

    String flightStatus;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    int price;

    long owner_id;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    Set<User> passengers;
}
