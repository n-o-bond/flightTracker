package com.example.flighttracker.controller;

import com.example.flighttracker.dto.UserDto;
import com.example.flighttracker.dto.UserTransformer;
import com.example.flighttracker.model.User;
import com.example.flighttracker.service.RoleService;
import com.example.flighttracker.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("isAuthenticated() or isAnonymous()")
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("user", new UserDto());
        return "create-user";
    }

    @PreAuthorize("isAuthenticated() or isAnonymous()")
    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("user") UserDto userDto, BindingResult result){
        if(result.hasErrors()){
            return "create-user";
        }
        userDto.setRoleId(2);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = UserTransformer.convertToEntity(userDto, roleService.readById(userDto.getRoleId()));
        userService.create(user);
        return "redirect:/home";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and authentication.principal.id == #id")
    @GetMapping("/{id}/read")
    public String read(@PathVariable long id, Model model){
        UserDto userDto = UserTransformer.convertToDto(userService.readById(id));
        model.addAttribute("user", userDto);
        return "user-info";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and authentication.principal.id == #id")
    @GetMapping("/{id}/update")
    public String update(@PathVariable long id, Model model){
        UserDto userDto = UserTransformer.convertToDto(userService.readById(id));
        model.addAttribute("user", userDto);
        model.addAttribute("roles", roleService.getAll());
        return "update-user";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and authentication.principal.id == #id")
    @PostMapping("/{id}/update")
    public String update(@PathVariable long id, Model model,
                         @Validated @ModelAttribute("user") UserDto userDto,
                         BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("roles", roleService.getAll());
            return "update-user";
        }
        User user = UserTransformer.convertToEntity(userDto, roleService.readById(userDto.getRoleId()));
        userService.update(user);
        return "redirect:/users/" + user.getId() + "/read";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and authentication.principal.id == #id")
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(((User) authentication.getPrincipal()).getId() == id){
            userService.delete(id);
            SecurityContextHolder.clearContext();
            return "redirect:/login-form";
        }
        userService.delete(id);
        return "redirect:/users/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public String getAll(Model model){
        model.addAttribute("users", userService.getAll());
        return "users-list";
    }
}
