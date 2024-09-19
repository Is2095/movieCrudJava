package com.movies.movie.controllers;

import com.movies.movie.models.Movie;
import com.movies.movie.repositories.MovieRepository;
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
    public MovieRepository movieRepository;

    @CrossOrigin
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable  Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping()
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
        Movie saveMovie = movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveMovie);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletemovie(@PathVariable Long id) {
        if(!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @PutMapping("/{id}")
        public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie updatedMovie) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedMovie.setId(id);
        Movie updateNewMovie = movieRepository.save(updatedMovie);
        return ResponseEntity.ok(updateNewMovie);
    }

    @CrossOrigin
    @GetMapping("/vote/{id}/{rating}")
    public ResponseEntity<Movie> votesMovie(@PathVariable Long id, @PathVariable double rating) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Optional<Movie> optional = movieRepository.findById(id);
        Movie voteMovie = optional.get();

        double newRating =( (voteMovie.getVotes() * voteMovie.getRating()) + rating ) / (voteMovie.getVotes() + 1);
        voteMovie.setVotes(voteMovie.getVotes() + 1);
        voteMovie.setRating(newRating);
        Movie savedMovie = movieRepository.save(voteMovie);
        return ResponseEntity.ok(savedMovie);
    }
}
