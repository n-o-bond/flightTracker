package com.example.flighttracker.repository;

import com.example.flighttracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Query(value = "select u from User u join fetch u.role where u.email =:email")
    User findByEmail(String email);
}