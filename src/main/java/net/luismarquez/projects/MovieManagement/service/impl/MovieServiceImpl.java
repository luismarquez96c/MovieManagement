package net.luismarquez.projects.MovieManagement.service.impl;

import net.luismarquez.projects.MovieManagement.dto.request.MovieSearchCriteria;
import net.luismarquez.projects.MovieManagement.dto.request.SaveMovie;
import net.luismarquez.projects.MovieManagement.dto.response.GetMovie;
import net.luismarquez.projects.MovieManagement.dto.response.GetMovieStatistic;
import net.luismarquez.projects.MovieManagement.exception.ObjectNotFoundException;
import net.luismarquez.projects.MovieManagement.mapper.MovieMapper;
import net.luismarquez.projects.MovieManagement.persistence.entity.Movie;
import net.luismarquez.projects.MovieManagement.persistence.repository.MovieCrudRepository;
import net.luismarquez.projects.MovieManagement.persistence.repository.RatingCrudRepository;
import net.luismarquez.projects.MovieManagement.persistence.specification.FindAllMoviesSpecification;
import net.luismarquez.projects.MovieManagement.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieCrudRepository movieCrudRepository;

    @Autowired
    private RatingCrudRepository ratingCrudRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<GetMovie> findAll(MovieSearchCriteria searchCriteria, Pageable pageable) {
        FindAllMoviesSpecification moviesSpecification = new FindAllMoviesSpecification(searchCriteria);

        Page<Movie> entities = movieCrudRepository.findAll(moviesSpecification, pageable);
        return entities.map(MovieMapper::toGetDto);
    }

    @Transactional(readOnly = true)
    @Override
    public GetMovieStatistic findOneById(Long id) {

        int totalRatings = ratingCrudRepository.countByMovieId(id);
        double averageRating = ratingCrudRepository.avgRatingByMovieId(id);
        int lowestRating = ratingCrudRepository.minRatingByMovieId(id);
        int highestRating = ratingCrudRepository.maxRatingByMovieId(id);

        return MovieMapper.toGetMovieStatisticDto(
                this.findOneEntityById(id),
                totalRatings,
                averageRating,
                lowestRating,
                highestRating
        );
    }

    @Transactional(readOnly = true)
    private Movie findOneEntityById(Long id) {
        return movieCrudRepository.findById(id)
                .orElseThrow( () -> new ObjectNotFoundException("[movie:"+ Long.toString(id) +"]"));
    }

    @Override
    public GetMovie createOne(SaveMovie saveDto) {
        Movie newMovie = MovieMapper.toEntity(saveDto);

        return MovieMapper.toGetDto(
                movieCrudRepository.save(newMovie)
        );
    }

    @Override
    public GetMovie updateOneById(Long id, SaveMovie saveDto) {
        Movie oldMovie = this.findOneEntityById(id);
        MovieMapper.updateEntity(oldMovie, saveDto);

        return MovieMapper.toGetDto(
                movieCrudRepository.save(oldMovie)
        );
    }

    @Override
    public void deleteOneById(Long id) {
        Movie movie = this.findOneEntityById(id);
        movieCrudRepository.delete(movie);
    }
}
