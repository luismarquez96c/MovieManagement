package net.luismarquez.projects.MovieManagement.persistence.repository;

import net.luismarquez.projects.MovieManagement.persistence.entity.Movie;
import net.luismarquez.projects.MovieManagement.util.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MovieCrudRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    //List<Movie> findByTitleContaining(String title);
    //List<Movie> findByGenre(MovieGenre genre);
    //List<Movie> findByGenreAndTitleContaining(MovieGenre genre, String title);
    //List<Movie> findByGenreAndTitleContainingAndReleaseYearGreaterThanEqual(MovieGenre genre, String title, Integer minReleaseYear);

}
