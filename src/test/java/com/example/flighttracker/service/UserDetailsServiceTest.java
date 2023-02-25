package com.example.flighttracker.service;

import com.example.flighttracker.model.User;
import com.example.flighttracker.repository.UserRepository;
import com.example.flighttracker.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User user;

    @BeforeEach
    public void beforeEach() {
        user = new User();
        user.setId(1L);
        user.setFirstName("Linda");
        user.setLastName("Braun");
        user.setEmail("linb@gmail.com");
        user.setPassword("linnibii");
    }

    @Test
    public void loadUserByUsernameExistingUser(){
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        UserDetails actual = userDetailsService.loadUserByUsername(user.getEmail());
        assertEquals(user, actual);
        verify(userRepository).findByEmail(anyString());
    }

}
