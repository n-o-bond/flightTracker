package com.example.flighttracker.repository;

import com.example.flighttracker.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql(scripts = {"/schema.sql","/data.sql"})
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void getRoleByIdTest(){
        Role actual = roleRepository.getOne(1L);

        Assertions.assertEquals("ADMIN", actual.getName());
    }
}
