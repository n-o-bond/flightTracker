package com.example.flighttracker.dto;

import com.example.flighttracker.model.Role;
import com.example.flighttracker.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer {

    public static UserDto convertToDto(User user){
        if (user == null){
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoleId(user.getRole().getId());

        return userDto;
    }

    public static User convertToEntity(UserDto userDto, Role role){
        if(userDto == null){
            return null;
        }

        User user = new User();

        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(role);

        return user;
    }
}
