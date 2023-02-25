package com.example.flighttracker.service;

import com.example.flighttracker.exception.NullEntityReferenceException;
import com.example.flighttracker.model.Role;
import com.example.flighttracker.repository.RoleRepository;
import com.example.flighttracker.service.impl.RoleServiceImpl;
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
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    Role role;

    @BeforeEach
    public void beforeEach() {
        role = new Role();
        role.setName("NewRole");
    }

    @Test
    public void createRoleTest() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        Role actual = roleService.create(role);
        assertEquals(actual, role);
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    public void createNullRoleTest() {
        Exception exception = assertThrows(NullEntityReferenceException.class, () -> roleService.create(null));
        assertTrue(exception.getMessage().contains("Role cannot be 'null'"));
        verify(roleRepository, times(0)).save(any(Role.class));
    }

    @Test
    public void readByRoleIdTest() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));

        Role actual = roleService.readById(role.getId());
        assertEquals(role, actual);
        verify(roleRepository).findById(anyLong());
    }

    @Test
    public void readByInvalidRoleIdTest() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());

        long notFoundId = 10L;
        assertThrows(EntityNotFoundException.class, () -> roleService.readById(notFoundId));
        verify(roleRepository).findById(anyLong());
    }

    @Test
    public void updateRoleTest() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        assertEquals(role, roleService.update(role));

        verify(roleRepository).save(any(Role.class));
        verify(roleRepository).findById(anyLong());
    }

    @Test
    void updateNonExistingRole() {
        assertThrows(EntityNotFoundException.class, () -> roleService.update(role));
        verify(roleRepository, times(0)).save(any(Role.class));
    }

    @Test
    public void updateNullRoleTest() {
        Exception exception = assertThrows(NullEntityReferenceException.class, () -> roleService.update(null));
        assertTrue(exception.getMessage().contains("Role cannot be 'null'"));
        verify(roleRepository, times(0)).save(any(Role.class));
    }

    @Test
    public void deleteRoleTest() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));

        roleService.delete(role.getId());
        verify(roleRepository).findById(anyLong());
    }

    @Test
    public void deleteEmptyRoleTest() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());

        long notFoundId = 10L;
        assertThrows(EntityNotFoundException.class, () -> roleService.delete(notFoundId));
        verify(roleRepository).findById(anyLong());
    }

    @Test
    public void getAllRolesTest() {
        var expected = new ArrayList<Role>();

        when(roleRepository.findAll()).thenReturn(expected);

        var actual = roleService.getAll();
        assertEquals(actual, expected);

        expected.add(role);
        actual = roleService.getAll();
        assertEquals(actual, expected);
        verify(roleRepository, times(2)).findAll();
    }
}
