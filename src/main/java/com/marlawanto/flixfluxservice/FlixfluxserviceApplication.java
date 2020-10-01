package com.marlawanto.flixfluxservice;

import com.marlawanto.flixfluxservice.model.Movie;
import com.marlawanto.flixfluxservice.model.MovieEvent;
import com.marlawanto.flixfluxservice.repository.MovieRepository;
import com.marlawanto.flixfluxservice.services.FluxFlixService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;
import static reactor.core.publisher.Flux.generate;

@SpringBootApplication
public class FlixfluxserviceApplication {
	@Bean
	RouterFunction<ServerResponse> routerFunction(MovieHandler handler) {
		return route(GET("/movies"), handler::all)
				.andRoute(GET("/movies/{id}"), handler::byId)
				.andRoute(GET("/movies/{id}/events"), handler::events);

	}


	public static void main(String[] args) {
		SpringApplication.run(FlixfluxserviceApplication.class, args);
	}
}


@Component
class MovieHandler{
	private final FluxFlixService ffs;

	MovieHandler(FluxFlixService ffs) {
		this.ffs = ffs;
	}

	public Mono<ServerResponse> all(ServerRequest serverRequest) {
		return ServerResponse.ok().body(ffs.getallMovies(), Movie.class);
	}

	public Mono<ServerResponse> byId(ServerRequest serverRequest) {
		return ServerResponse.ok().body(ffs.getMovieById(serverRequest.pathVariable("id")), Movie.class);
	}

	public Mono<ServerResponse> events(ServerRequest serverRequest) {
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(ffs.getEvents(serverRequest.pathVariable("id")), MovieEvent.class);
	}

}

@Component
class SampleDataInitializer implements ApplicationRunner {

	private final MovieRepository mr;

	SampleDataInitializer(MovieRepository mr) {
		this.mr = mr;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Flux<Movie> movieFlux = Flux.just("Silence of the lambda", "Suzana", "back to the future")
				.map(m -> new Movie(null, m))
				.flatMap(mr::save);

		mr.deleteAll()
				.thenMany(movieFlux)
				.thenMany(mr.findAll())
				.subscribe(System.out::println);
	}
}