package com.marlawanto.flixfluxservice;

import com.marlawanto.flixfluxservice.model.Movie;
import com.marlawanto.flixfluxservice.repository.MovieRepository;
import com.marlawanto.flixfluxservice.services.FluxFlixService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Flux.generate;

@SpringBootApplication
public class FlixfluxserviceApplication {
//	@Bean
//	RouterFunction<ServerResponse> routerFunction(){
//		RouterFunctions.route(RequestPredicates.GET("/movies"), new HandlerFunction<ServerResponse>() {
//			@Override
//			public Mono<ServerResponse> handle(ServerRequest serverRequest) {
//				return null;
//			}
//		});
//		return null;
//	}

	public static void main(String[] args) {
		SpringApplication.run(FlixfluxserviceApplication.class, args);
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