package net.luismarquez.projects.MovieManagement.service;

import net.luismarquez.projects.MovieManagement.persistence.entity.Movie;
import net.luismarquez.projects.MovieManagement.util.MovieGenre;

import java.util.List;

public interface MovieService {

    List<Movie> findAll();
    List<Movie> findAllByTitle(String title);
    List<Movie> findAllByGenre(MovieGenre genre);
    List<Movie> findAllByGenreAndTitle(MovieGenre genre, String title);

    Movie findOneById(Long id);
    Movie createOne(Movie movie);
    Movie updateOneById(Long id, Movie movie);
    void deleteOneById(Long id);

}
