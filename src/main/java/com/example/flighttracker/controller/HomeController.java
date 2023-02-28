package com.example.flighttracker.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @PreAuthorize("isAnonymous() or isAuthenticated()")
    @GetMapping("/")
    public String mainPage(){
        return "redirect:/flights/all";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/home")
    public String home(){
        return "home";
    }
}
