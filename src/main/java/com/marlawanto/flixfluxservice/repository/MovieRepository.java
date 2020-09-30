package com.marlawanto.flixfluxservice.repository;

import com.marlawanto.flixfluxservice.model.Movie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {
}
