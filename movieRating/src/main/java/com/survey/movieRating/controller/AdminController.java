package com.survey.movieRating.controller;

import com.survey.movieRating.model.MovieToRate;
import com.survey.movieRating.service.MovieService;
import com.survey.movieRating.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/app")
public class AdminController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String getMovie(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "Admin";
    }

    @PostMapping("/movie/add")
    public String addMovie(@RequestParam("file") MultipartFile logo,
                           @RequestParam("name") String name) throws IOException {
        movieService.saveMovie(name, logo);
        return "redirect:/app/admin";
    }

    @PostMapping("/movie/remove")
    public String removeMovie(@RequestParam("id") Long id) {
        movieService.removeMovie(id);
        return "redirect:/app/admin";
    }


    @GetMapping("/movie/import")
    public String importMovieGalleryTest(Model model) throws IOException {
        movieService.importMoviesFromExcel();
        return "redirect:/app/admin";
    }
}
