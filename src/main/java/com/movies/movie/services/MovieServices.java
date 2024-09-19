package com.movies.movie.services;

import com.movies.movie.models.Movie;
import com.movies.movie.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServices {


    @Autowired
    public MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return (List<Movie>) movieRepository.findAll();
    }

    public ResponseEntity<Movie> getMovieById(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Movie> createMovie(Movie movie){
        Movie saveMovie = movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveMovie);
    }

    public ResponseEntity<Void> deletemovie(Long id) {
        if(!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Movie> updateMovie(Long id, Movie updatedMovie) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedMovie.setId(id);
        Movie updateNewMovie = movieRepository.save(updatedMovie);
        return ResponseEntity.ok(updateNewMovie);
    }

    public ResponseEntity<Movie> votesMovie(Long id,double rating) {
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
