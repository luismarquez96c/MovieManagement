package net.luismarquez.projects.MovieManagement.service.impl;

import net.luismarquez.projects.MovieManagement.exception.ObjectNotFoundException;
import net.luismarquez.projects.MovieManagement.persistence.entity.Movie;
import net.luismarquez.projects.MovieManagement.persistence.repository.MovieCrudRepository;
import net.luismarquez.projects.MovieManagement.service.MovieService;
import net.luismarquez.projects.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieCrudRepository movieCrudRepository;

    @Override
    public List<Movie> findAll() {
        return movieCrudRepository.findAll();
    }

    @Override
    public List<Movie> findAllByTitle(String title) {
        return movieCrudRepository.findByTitleContaining(title);
    }

    @Override
    public List<Movie> findAllByGenre(MovieGenre genre) {
        return movieCrudRepository.findByGenre(genre);
    }

    @Override
    public List<Movie> findAllByGenreAndTitle(MovieGenre genre, String title) {
        return movieCrudRepository.findByGenreAndTitleContaining(genre, title);
    }

    @Override
    public Movie findOneById(Long id) {
        return movieCrudRepository.findById(id)
                .orElseThrow( () -> new ObjectNotFoundException("[movie:"+ Long.toString(id) +"]"));
    }

    @Override
    public Movie createOne(Movie movie) {
        return movieCrudRepository.save(movie);
    }

    @Override
    public Movie updateOneById(Long id, Movie newMovie) {
        Movie oldMovie = this.findOneById(id);
        oldMovie.setGenre(newMovie.getGenre());
        oldMovie.setReleaseYear(newMovie.getReleaseYear());
        oldMovie.setTitle(newMovie.getTitle());
        oldMovie.setDirector(newMovie.getDirector());

        return movieCrudRepository.save(oldMovie);
    }

    @Override
    public void deleteOneById(Long id) {
        Movie movie = this.findOneById(id);
        movieCrudRepository.delete(movie);
    }
}
