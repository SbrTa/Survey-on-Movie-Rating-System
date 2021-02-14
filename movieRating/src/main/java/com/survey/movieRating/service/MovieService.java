package com.survey.movieRating.service;

import com.survey.movieRating.entity.Movie;
import com.survey.movieRating.entity.RateRatingSystem;
import com.survey.movieRating.entity.Rating;
import com.survey.movieRating.enums.RatingSystem;
import com.survey.movieRating.model.MovieSourceModel;
import com.survey.movieRating.model.MovieToRate;
import com.survey.movieRating.repository.MovieRepository;
import com.survey.movieRating.repository.RateRatingSystemRepository;
import com.survey.movieRating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieService extends BaseService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private DataImportExportService dataExportService;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private RateRatingSystemRepository rateRatingSystemRepository;

    public void saveMovie(String name, MultipartFile logo) throws IOException {
        if (name == null || "".equals(name.trim()) || logo.isEmpty()) {
            return;
        }
        if (movieRepository.countByName(name.trim()) > 0) {
            return;
        }
        Movie movie = new Movie();
        movie.setName(name.trim());
        movie.setLogo(this.convertMultipartToBase64Img(logo));
        movieRepository.save(movie);
    }

    public void removeMovie(Long id) {
        movieRepository.delete(id);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public void importMoviesFromExcel() throws IOException {
        List<MovieSourceModel> movieSources = dataExportService.readFromExcel();
        movieSources.remove(0);
        for (MovieSourceModel model : movieSources) {
            this.addMovieFromResource(model);
        }
    }

    private void addMovieFromResource(MovieSourceModel model) throws IOException {
        if (movieRepository.countByName(model.getName()) > 0) {
            return;
        }
        Resource resource = new ClassPathResource("posters/" + model.getPath());
        InputStream input = resource.getInputStream();
        Movie movie = new Movie();
        movie.setName(model.getName());
        movie.setLogo(this.convertInputStreamToBase64Img(input));
        movieRepository.save(movie);
    }

    public void addSelectedMovies(List<Long> selectedIds) {
        List<Movie> movies = movieRepository.findAll();
        List<Movie> selected = new ArrayList<>();
        for (Movie movie : movies) {
            for (Long id : selectedIds) {
                if (movie.getId().equals(id)) {
                    selected.add(movie);
                }
            }
        }

        List<Rating> prevRatings = ratingRepository.findAllByUserId(getCurrentUser().getId());

        for (Rating rating : prevRatings) {
            ratingRepository.delete(rating);
        }

        Long serial = 1L;
        for (Movie movie : selected) {
            for (RatingSystem system : RatingSystem.values()) {
                Rating rating = new Rating();
                rating.setUserId(getCurrentUser().getId());
                rating.setMovieId(movie.getId());
                rating.setRatingSystem((long) system.getRatingSystem());
                rating.setRatingSystemName(system.getRatingSystemName());
                rating.setIsRated(false);
                rating.setSerial(serial++);
                ratingRepository.save(rating);
            }
        }

        List<Rating> ratings = ratingRepository.findAllByUserId(getCurrentUser().getId());
        for (Rating rating : ratings) {
            System.out.println(rating);
        }
    }

    public Movie getNextMovieToRate() {


        return null;
    }

    public MovieToRate getMovieToRate(Long serial) {
        Rating rating = ratingRepository.findByUserIdAndSerial(getCurrentUser().getId(), serial);
        Movie movie = movieRepository.getOne(rating.getMovieId());
        MovieToRate movieToRate = new MovieToRate();
        movieToRate.setId(movie.getId());
        movieToRate.setName(movie.getName());
        movieToRate.setLogo(movie.getLogo());
        movieToRate.setRatingId(rating.getId());
        movieToRate.setRatingSystem(rating.getRatingSystem());
        movieToRate.setSerial(rating.getSerial());
        return movieToRate;
    }

    public Long saveRating(Long ratingId, Long rate) {
        Rating rating = ratingRepository.getOne(ratingId);
        rating.setRating(rate);

        Long next = rating.getSerial() + 1;
        if (ratingRepository.findByUserIdAndSerial(getCurrentUser().getId(), next) == null) {
            next = null;
        }
        return next;
    }

    public List<Movie> getSelectedMovies() {
        List<Rating> ratings = ratingRepository.findAllByUserId(getCurrentUser().getId());
        Map<Long, Boolean> isSelected = new HashMap<>();
        List<Long> selected = new ArrayList<>();
        for (Rating rating : ratings) {
            if (isSelected.get(rating.getMovieId()) == null) {
                isSelected.put(rating.getMovieId(), true);
                selected.add(rating.getMovieId());
            }
        }
        List<Movie> movies = new ArrayList<>();
        for (Long movieId : selected) {
            Movie movie = movieRepository.getOne(movieId);
            movies.add(movie);
        }
        return movies;
    }

    public void saveRatingOfRatingSystems(Long colorCircle, Long colorStar, Long colorEmo, Long bwCircle, Long bwEmo, Long bwStar) {
        RateRatingSystem model = new RateRatingSystem();
        model.setColorCircle(colorCircle);
        model.setColorStar(colorStar);
        model.setColorEmo(colorEmo);
//        model.setGradient(gradient);
        model.setBwCircle(bwCircle);
        model.setBwEmo(bwEmo);
        model.setBwStar(bwStar);
        model.setUserId(getCurrentUser().getId());

        RateRatingSystem system = rateRatingSystemRepository.save(model);
        System.out.println("\n\n\nUser Rating on rating system saved : ");
        System.out.println(system);
    }
}
