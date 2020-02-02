package com.survey.movieRating.controller;

import com.survey.movieRating.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "redirect:/app";
    }

    @GetMapping("/app")
    public String welcome(){
        return "Welcome";
    }
    @PostMapping("/app")
    public String welcomePOST(){
        userService.createUser();
        return "redirect:/app/userChoice";
    }

}
