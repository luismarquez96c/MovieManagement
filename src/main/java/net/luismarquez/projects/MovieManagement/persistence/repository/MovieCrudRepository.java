package net.luismarquez.projects.MovieManagement.persistence.repository;

import net.luismarquez.projects.MovieManagement.persistence.entity.Movie;
import net.luismarquez.projects.MovieManagement.util.MovieGenre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MovieCrudRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    //Opción 1: Utilización de objetos de configuración Sort y Pageable con o sin paginación
    List<Movie> findAllByGenre(MovieGenre genre, Sort sort);
    Page<Movie> findAllByGenre(MovieGenre genre, Pageable pageable);



    
    //Opción 2: Utilización de palabra reservar orderBy con y sin paginación
    List<Movie> findAllByGenreOrderByTitle(MovieGenre genre);
    Page<Movie> findAllByGenreOrderByTitle(MovieGenre genre, Pageable pageable);

}
