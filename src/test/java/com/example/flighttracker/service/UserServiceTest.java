package com.example.flighttracker.service;

import com.example.flighttracker.exception.NullEntityReferenceException;
import com.example.flighttracker.model.User;
import com.example.flighttracker.repository.UserRepository;
import com.example.flighttracker.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

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
    public void createValidUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User actual = userService.create(user);
        assertEquals(actual, user);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void createUserIsNull() {
        Exception exception = assertThrows(NullEntityReferenceException.class, () -> userService.create(null));
        assertTrue(exception.getMessage().contains("User cannot be 'null'"));
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void readByIdUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User actual = userService.readById(user.getId());
        assertEquals(user, actual);
        verify(userRepository).findById(anyLong());
    }

    @Test
    public void readByIdInvalidUserId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        long notFoundId = 10L;
        Exception exception = assertThrows(EntityNotFoundException.class, () -> userService.readById(notFoundId));
        assertTrue(exception.getMessage().contains("User with id " + notFoundId + " not found"));
        verify(userRepository).findById(anyLong());
    }

    @Test
    public void updateExistingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        assertEquals(user, userService.update(user));

        verify(userRepository).save(any(User.class));
        verify(userRepository).findById(anyLong());
    }

    @Test
    public void updateNonExistingUser(){
        assertThrows(EntityNotFoundException.class, () -> userService.update(user));
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void updateUserIsNull() {
        Exception exception = assertThrows(NullEntityReferenceException.class, () -> userService.update(null));
        assertTrue(exception.getMessage().contains("User cannot be 'null'"));
        verify(userRepository, times(0)).save(any(User.class));

    }

    @Test
    public void deleteExistingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        userService.delete(user.getId());
        verify(userRepository).findById(anyLong());
    }

    @Test
    public void deleteInvalidUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        long notFoundId = 10L;
        Exception exception = assertThrows(EntityNotFoundException.class, () -> userService.delete(notFoundId));
        assertTrue(exception.getMessage().contains("User with id " + notFoundId + " not found"));
        verify(userRepository).findById(anyLong());
    }

    @Test
    public void getAllUsers() {
        var expected = new ArrayList<User>();

        when(userRepository.findAll()).thenReturn(expected);

        var actual = userService.getAll();
        assertEquals(actual, expected);

        expected.add(user);
        actual = userService.getAll();
        assertEquals(actual, expected);
        verify(userRepository, times(2)).findAll();
    }
}
