package com.marlawanto.flixfluxservice.services;

import com.marlawanto.flixfluxservice.model.Movie;
import com.marlawanto.flixfluxservice.model.MovieEvent;
import com.marlawanto.flixfluxservice.repository.MovieRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;

@Service
public class FluxFlixService {
    private final MovieRepository movieRepository;

    FluxFlixService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Flux<Movie> getallMovies() {
        return this.movieRepository.findAll();
    }

    public Mono<Movie> getMovieById(String id) {
        return this.movieRepository.findById(id);
    }

    public Flux<MovieEvent> getEvents(String movieId) {
        return Flux.<MovieEvent>generate(sink -> sink.next(new MovieEvent(movieId, new Date())))
                // scheduler behind the scene to delay element
                .delayElements(Duration.ofSeconds(1));
    }
}
