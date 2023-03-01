package com.example.flighttracker.controller;

import com.example.flighttracker.dto.UserDto;
import com.example.flighttracker.dto.UserTransformer;
import com.example.flighttracker.model.User;
import com.example.flighttracker.service.RoleService;
import com.example.flighttracker.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Test
    public void createUserGet() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/users/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.view().name("create-user"));
    }

    @Test
    @Transactional
    public void createUserPost() throws Exception {

        User user = new User();
        user.setFirstName("Hiro");
        user.setLastName("Jenkin");
        user.setEmail("hiro@gmail.com");
        user.setPassword("0333");
        user.setRole(roleService.readById(2));

        UserDto userDto = UserTransformer.convertToDto(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .flashAttr("user", userDto))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/home"));

        //Checking incorrect User creation
        userDto.setFirstName("Name");
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .flashAttr("user", userDto))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.view().name("error"));

        Assertions.assertThrows(EntityNotFoundException.class, ()->userService.readById(1L));
    }

    @Test
    @WithUserDetails(value = "nora@mail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void readUserTest() throws Exception{
        long userId = 6L;
        UserDto expected = UserTransformer.convertToDto(userService.readById(userId));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + userId + "/read"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", expected));
    }

    @Test
    @Transactional
    @WithUserDetails(value = "nora@mail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void updateUserGet() throws Exception
    {
        long userId = 6L;
        UserDto expected = UserTransformer.convertToDto(userService.readById(userId));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + userId + "/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("roles"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", expected))
                .andExpect(MockMvcResultMatchers.model().attribute("roles", roleService.getAll()));
    }

    @Test
    @Transactional
    @WithUserDetails(value = "nora@mail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void updateUserPost() throws Exception{

        long userId = 6L;
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setFirstName("Nori");
        userDto.setLastName("White");
        userDto.setPassword("3333");
        userDto.setEmail("nora@gmail.com");
        userDto.setRoleId(2);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/" + userId + "/update")
                        .flashAttr("user", userDto).flashAttr("roles", roleService.getAll()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/users/" + userId + "/read"));

        UserDto actual = UserTransformer.convertToDto(userService.readById(userId));
        Assertions.assertEquals(userDto, actual);

        //Checking incorrect User update
        long incorrectId = 1L;
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/" + incorrectId + "/update")
                        .flashAttr("user", userDto).flashAttr("roles", roleService.getAll()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.view().name("error"));

        Assertions.assertNotEquals(userDto, actual);
    }


    @Test
    @Transactional
    public void deleteUserGet() throws Exception{
        long userId = 4L;

        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + userId + "/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @WithUserDetails(value = "nick@mail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void getAllUsers() throws Exception {
        List<User> expected = userService.getAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/users/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", expected));
    }

}
