package com.movies.movie.controllers;

import com.movies.movie.models.Movie;
import com.movies.movie.repositories.MovieRepository;
import com.movies.movie.services.MovieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieServices service;

    @CrossOrigin
    @GetMapping
    public List<Movie> getAllMovies() {
        return service.getAllMovies();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable  Long id) {
        return service.getMovieById(id);
    }

    @CrossOrigin
    @PostMapping()
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
        return service.createMovie(movie);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletemovie(@PathVariable Long id) {
        return service.deletemovie(id);
    }

    @CrossOrigin
    @PutMapping("/{id}")
        public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie updatedMovie) {
        return service.updateMovie(id, updatedMovie);
    }

    @CrossOrigin
    @GetMapping("/vote/{id}/{rating}")
    public ResponseEntity<Movie> votesMovie(@PathVariable Long id, @PathVariable double rating) {
        return service.votesMovie(id, rating);
    }
}
