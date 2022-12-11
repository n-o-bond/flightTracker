package com.example.flighttracker.controller;

import com.example.flighttracker.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("isAnonymous() or isAuthenticated()")
    @GetMapping("/")
    public String mainPage(){
        return "redirect:/flights/all";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("users", userService.getAll());
        return "home";
    }
}
