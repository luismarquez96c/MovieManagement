package net.luismarquez.projects.MovieManagement;

import net.luismarquez.projects.MovieManagement.persistence.repository.MovieCrudRepository;
import net.luismarquez.projects.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class MovieManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieManagementApplication.class, args);
	}

	@Autowired
	private MovieCrudRepository movieCrudRepository;

	@Bean
	public CommandLineRunner testFindAllMoviesByGenreOrderBy(){
		return args -> {
			System.out.println("Peliculas de DRAMA ordenadas por título");
			Sort.Direction direction = Sort.Direction.fromString("DESC");
			Sort sort = Sort.by(direction, "releaseYear").and(Sort.by(Sort.Direction.ASC, "id"));
			Pageable pageable = PageRequest.of(0, 10, sort);

			movieCrudRepository.findAllByGenre(MovieGenre.DRAMA, pageable)
					.forEach(System.out::println);

		};
	}

}
