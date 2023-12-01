package net.luismarquez.projects.MovieManagement.service.impl;

import net.luismarquez.projects.MovieManagement.exception.ObjectNotFoundException;
import net.luismarquez.projects.MovieManagement.persistence.entity.Rating;
import net.luismarquez.projects.MovieManagement.persistence.repository.RatingCrudRepository;
import net.luismarquez.projects.MovieManagement.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingCrudRepository ratingCrudRepository;

    @Override
    public List<Rating> findAll() {
        return ratingCrudRepository.findAll();
    }

    @Override
    public List<Rating> findAllByMovieId(Long movieId) {
        return ratingCrudRepository.findByMovieId(movieId);
    }

    @Override
    public List<Rating> findAllByUsername(String username) {
        return ratingCrudRepository.findByUsername(username);
    }

    @Override
    public Rating findOneById(Long id) {
        return ratingCrudRepository.findById(id)
                .orElseThrow( () -> new ObjectNotFoundException("[rating:" + Long.toString(id) + "]"));
    }

    @Override
    public Rating createOne(Rating rating) {
        return ratingCrudRepository.save(rating);
    }

    @Override
    public Rating updateOneById(Long id, Rating rating) {
        Rating oldRating = this.findOneById(id);
        oldRating.setUserId(rating.getUserId());
        oldRating.setMovieId(rating.getMovieId());

        return ratingCrudRepository.save(oldRating);
    }

    @Override
    public void deleteOneById(Long id) {
        if(ratingCrudRepository.existsById(id)){
            ratingCrudRepository.deleteById(id);
            return;
        }

        throw new ObjectNotFoundException("[rating:" + Long.toString(id) + "]");
    }
}
