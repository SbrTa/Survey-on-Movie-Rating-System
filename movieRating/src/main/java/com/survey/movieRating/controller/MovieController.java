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
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private UserService userService;


    @GetMapping("/movie/gallery")
    public String getMovieGallery(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "MovieGallery";
    }

    @PostMapping("/movie/gallery")
    public String postMovieGallery(@RequestParam("selectedMovie") List<Long> selectedIds, Model model) {
        movieService.addSelectedMovies(selectedIds);
        return "redirect:/app/movie/selected";
    }

    @GetMapping("/movie/selected")
    public String getSelectedMovies(Model model) {
        model.addAttribute("movies", movieService.getSelectedMovies());
        return "SelectedMovies";
    }

    @GetMapping("/movie/rating/{id}")
    public String getMovieRating(@PathVariable(name = "id") Long serial, Model model) {
        if (serial==0){
            return "redirect:/app/movie/selected";
        }
        MovieToRate movieToRate = movieService.getMovieToRate(serial);
        if (movieToRate==null){
            return "";
        }
        model.addAttribute("movie", movieToRate);
        return "IndividualMovie";
    }

    @PostMapping("/movie/rating")
    public String setMovieRating(@RequestParam(name = "ratingId") Long ratingId,
                                 @RequestParam(name = "ratingValue") Long rating) {
        Long next = movieService.saveRating(ratingId,rating);
        if (next==null){
            return "redirect:/app/rating-system/rating";
        }
        return "redirect:/app/movie/rating/"+next;
    }

    @GetMapping("/rating-system/rating")
    public String getRatingOfRatingSystems() {
        return "RateRatingSystem";
    }

    @PostMapping("/rating-system/rating")
    public String postRatingOfRatingSystems(@RequestParam("colorCircle") Long colorCircle,
                                 @RequestParam("colorStar") Long colorStar,
                                 @RequestParam("colorEmo") Long colorEmo,
//                                 @RequestParam("gradient") Long gradient,
                                 @RequestParam("bwCircle") Long bwCircle,
                                 @RequestParam("bwEmo") Long bwEmo,
                                 @RequestParam("bwStar") Long bwStar)  {
        movieService.saveRatingOfRatingSystems(colorCircle,colorStar,colorEmo,bwCircle,bwEmo,bwStar);
        return "redirect:/app/rating-system/fav";
    }

    @GetMapping("/rating-system/fav")
    public String favRatingSystem() {
        return "FavRatingSystem";
    }

    @PostMapping("/rating-system/fav")
    public String favRatingSystem(@RequestParam("favSystem") Long favSystem)  {
        userService.saveFavRatingSystem(favSystem);
        return "redirect:/app/raffle";
    }

    @GetMapping("/raffle")
    public String getRaffle() {
        return "Raffle";
    }

    @PostMapping("/raffle")
    public String postRaffle(@RequestParam("email") String email)  {
        userService.saveUserEmail(email);
        return "redirect:/app/thanks";
    }

    @GetMapping("/thanks")
    public String thanks() {
        return "Thanks";
    }



}
