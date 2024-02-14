package net.luismarquez.projects.MovieManagement.service;

import net.luismarquez.projects.MovieManagement.dto.request.MovieSearchCriteria;
import net.luismarquez.projects.MovieManagement.dto.request.SaveMovie;
import net.luismarquez.projects.MovieManagement.dto.response.GetMovie;
import net.luismarquez.projects.MovieManagement.persistence.entity.Movie;
import net.luismarquez.projects.MovieManagement.util.MovieGenre;

import java.util.List;

public interface MovieService {

    List<GetMovie> findAll(MovieSearchCriteria searchCriteria);
//    List<GetMovie> findAllByTitle(String title);
//    List<GetMovie> findAllByGenre(MovieGenre genre);
//    List<GetMovie> findAllByGenreAndTitle(MovieGenre genre, String title);
//    List<GetMovie> findAllByGenreAndTitleAndMinReleaseYear(MovieGenre genre, String title, Integer minReleaseYear);

    GetMovie findOneById(Long id);
    GetMovie createOne(SaveMovie saveDto);
    GetMovie updateOneById(Long id, SaveMovie saveDto);
    void deleteOneById(Long id);

}
