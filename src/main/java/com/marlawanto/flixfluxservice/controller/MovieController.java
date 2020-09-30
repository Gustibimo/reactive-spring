package com.marlawanto.flixfluxservice.controller;

import com.marlawanto.flixfluxservice.model.Movie;
import com.marlawanto.flixfluxservice.model.MovieEvent;
import com.marlawanto.flixfluxservice.services.FluxFlixService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@RestController
//@RequestMapping("/movies")
//public class MovieController {
//    private final FluxFlixService fluxFlixService;
//
//    MovieController(FluxFlixService fluxFlixService) {
//        this.fluxFlixService = fluxFlixService;
//    }
//
//    @GetMapping
//    public Flux<Movie> all() {
//        return this.fluxFlixService.getallMovies();
//    }
//
//    @GetMapping("/{id}")
//    public Mono<Movie> byId(@PathVariable String id) {
//        return this.fluxFlixService.getMovieById(id);
//    }
//
//    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<MovieEvent> events(@PathVariable String id) {
//        return this.fluxFlixService.getEvents(id);
//    }
//
//}
