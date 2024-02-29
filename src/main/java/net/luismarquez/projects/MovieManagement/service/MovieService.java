package net.luismarquez.projects.MovieManagement.service;

import net.luismarquez.projects.MovieManagement.dto.request.MovieSearchCriteria;
import net.luismarquez.projects.MovieManagement.dto.request.SaveMovie;
import net.luismarquez.projects.MovieManagement.dto.response.GetMovie;
import net.luismarquez.projects.MovieManagement.dto.response.GetMovieStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {

    Page<GetMovie> findAll(MovieSearchCriteria searchCriteria, Pageable pageable);
    GetMovieStatistic findOneById(Long id);
    GetMovie createOne(SaveMovie saveDto);
    GetMovie updateOneById(Long id, SaveMovie saveDto);
    void deleteOneById(Long id);

}
