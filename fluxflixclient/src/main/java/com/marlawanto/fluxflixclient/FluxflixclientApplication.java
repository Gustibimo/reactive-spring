package com.marlawanto.fluxflixclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;

@SpringBootApplication
public class FluxflixclientApplication {
	@Bean
	WebClient client(){
		return WebClient.builder()
				.filter(ExchangeFilterFunctions.basicAuthentication("gbimo", "pw"))
				.baseUrl("http://localhost:8040/movies")
				.build();
	}

	@Bean
	CommandLineRunner clirun(WebClient client){
		return args ->  {
			client.get()
					.retrieve()
					.bodyToFlux(Movie.class)
					.filter(movie -> movie.getTitle().equalsIgnoreCase("suzana"))
					.flatMap(movie -> client.get()
						.uri("/{id}/events", movie.getId()).retrieve().bodyToFlux(MovieEvent.class))
					.subscribe(System.out::println);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(FluxflixclientApplication.class, args);
	}

}



@Data
@NoArgsConstructor
@AllArgsConstructor
class MovieEvent {
	private String movieId;
	private Date dateViewed;
}

	@Data
	@AllArgsConstructor
	class Movie {
		private String id;
		private String title;
	}



