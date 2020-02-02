package com.survey.movieRating.controller;

import com.survey.movieRating.service.UserChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/app")
public class UserChoiceController {
    @Autowired
    UserChoiceService userChoiceService;

    @GetMapping("/userChoice")
    public String getUserChoice(){
        return "UsersChoice";
    }

    @PostMapping("/userChoice")
    public String saveUserChoice(@RequestParam("talkativeLevel") int talkativeLevel,
                             @RequestParam("findFaultLevel") int findFaultLevel,
                             @RequestParam("thoroughJobLevel") int thoroughJobLevel,
                             @RequestParam("depressionLevel") int depressionLevel)  {
        userChoiceService.saveUserChoice(talkativeLevel,findFaultLevel,thoroughJobLevel,depressionLevel);
        return "redirect:/app/movie/gallery";
    }
}
