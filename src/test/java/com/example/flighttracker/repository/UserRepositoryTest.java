package com.example.flighttracker.repository;

import com.example.flighttracker.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql(scripts = {"/schema.sql","/data.sql"})
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findByEmailTest(){
        User expected = new User();
        expected.setId(1L);
        expected.setFirstName("Mark");
        expected.setLastName("Huang");
        expected.setEmail("mark@gmail.com");
        expected.setPassword("markPassword8");
        expected.setRole(roleRepository.getOne(2L));

        userRepository.save(expected);
        User actual = userRepository.findByEmail("mark@gmail.com");;

        Assertions.assertEquals(expected, actual);
    }
}
