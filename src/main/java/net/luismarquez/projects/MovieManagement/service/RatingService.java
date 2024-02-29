package net.luismarquez.projects.MovieManagement.service;

import net.luismarquez.projects.MovieManagement.dto.request.SaveRating;
import net.luismarquez.projects.MovieManagement.dto.response.GetCompleteRating;
import net.luismarquez.projects.MovieManagement.dto.response.GetMovie;
import net.luismarquez.projects.MovieManagement.dto.response.GetUser;
import net.luismarquez.projects.MovieManagement.persistence.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RatingService {

    Page<GetCompleteRating> findAll(Pageable pageable);
    Page<GetMovie.GetRating> findAllByMovieId(Long movieId, Pageable pageable);
    Page<GetUser.GetRating> findAllByUsername(String username, Pageable pageable);
    GetCompleteRating findOneById(Long id);
    GetCompleteRating createOne(SaveRating saveDto);
    GetCompleteRating updateOneById(Long id, SaveRating saveDto);
    void deleteOneById(Long id);

}
