package net.luismarquez.projects.MovieManagement.service.impl;

import jakarta.persistence.EntityManager;
import net.luismarquez.projects.MovieManagement.dto.request.SaveRating;
import net.luismarquez.projects.MovieManagement.dto.response.GetCompleteRating;
import net.luismarquez.projects.MovieManagement.exception.DuplicateRatingException;
import net.luismarquez.projects.MovieManagement.exception.ObjectNotFoundException;
import net.luismarquez.projects.MovieManagement.mapper.RatingMapper;
import net.luismarquez.projects.MovieManagement.persistence.entity.Rating;
import net.luismarquez.projects.MovieManagement.persistence.entity.User;
import net.luismarquez.projects.MovieManagement.persistence.repository.RatingCrudRepository;
import net.luismarquez.projects.MovieManagement.service.RatingService;
import net.luismarquez.projects.MovieManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingCrudRepository ratingCrudRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    @Override
    public Page<GetCompleteRating> findAll(Pageable pageable) {
        return ratingCrudRepository.findAll(pageable).map(RatingMapper::toGetCompleteRatingDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetCompleteRating> findAllByMovieId(Long movieId, Pageable pageable) {
        return ratingCrudRepository.findByMovieId(movieId, pageable).map(RatingMapper::toGetCompleteRatingDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetCompleteRating> findAllByUsername(String username, Pageable pageable) {
        return ratingCrudRepository.findByUsername(username, pageable).map(RatingMapper::toGetCompleteRatingDto);
    }

    @Transactional(readOnly = true)
    @Override
    public GetCompleteRating findOneById(Long id) {
        return RatingMapper.toGetCompleteRatingDto(this.findOneEntityById(id));
    }

    private Rating findOneEntityById(Long id){
        return ratingCrudRepository.findById(id)
                .orElseThrow( () -> new ObjectNotFoundException("[rating:" + Long.toString(id) + "]"));
    }

    @Override
    public GetCompleteRating createOne(SaveRating saveDto) {

        //Opción 1
        boolean ratingExists = ratingCrudRepository.existsByMovieIdAndUserUsername(saveDto.movieId(), saveDto.username());
        if(ratingExists){
            throw new DuplicateRatingException(saveDto.username(), saveDto.movieId());
        }

        //Opción 2
//        Long ratingId = ratingCrudRepository.getRatingIdByMovieIdAndUsername(saveDto.movieId(), saveDto.username());
//        if(ratingId != null && ratingId.longValue() > 0){
//            return this.updateOneById(ratingId, saveDto);
//        }

        User userEntity = userService.findOneEntityByUsername(saveDto.username());
        Rating entity = RatingMapper.toEntity(saveDto, userEntity.getId());
        ratingCrudRepository.save(entity);
        entityManager.detach(entity);

        return ratingCrudRepository.findById(entity.getId())
                .map(RatingMapper::toGetCompleteRatingDto)
                .get();
    }

    @Override
    public GetCompleteRating updateOneById(Long id, SaveRating saveDto) {
        Rating oldRating = this.findOneEntityById(id);
        User userEntity = userService.findOneEntityByUsername(saveDto.username());
        RatingMapper.updateEntity(oldRating, saveDto, userEntity.getId());

        return RatingMapper.toGetCompleteRatingDto(ratingCrudRepository.save(oldRating));
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
