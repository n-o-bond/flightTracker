package com.example.flighttracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login-form")
    public String login(){
        return "login-page";
    }

    @GetMapping("/accessDenied")
    public ModelAndView accessDenied(Principal user){
        ModelAndView model = new ModelAndView();
        if(user != null){
            model.addObject("error", "Sorry, you do not have permission to view this page!");
        }
        model.setViewName("accessDenied");
        return model;
    }
}
