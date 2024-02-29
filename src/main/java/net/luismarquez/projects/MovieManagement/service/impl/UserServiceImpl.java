package net.luismarquez.projects.MovieManagement.service.impl;

import jakarta.persistence.criteria.Predicate;
import net.luismarquez.projects.MovieManagement.dto.request.SaveUser;
import net.luismarquez.projects.MovieManagement.dto.response.GetUser;
import net.luismarquez.projects.MovieManagement.dto.response.GetUserStatistic;
import net.luismarquez.projects.MovieManagement.exception.ObjectNotFoundException;
import net.luismarquez.projects.MovieManagement.mapper.UserMapper;
import net.luismarquez.projects.MovieManagement.persistence.entity.Movie;
import net.luismarquez.projects.MovieManagement.persistence.entity.User;
import net.luismarquez.projects.MovieManagement.persistence.repository.MovieCrudRepository;
import net.luismarquez.projects.MovieManagement.persistence.repository.RatingCrudRepository;
import net.luismarquez.projects.MovieManagement.persistence.repository.UserCrudRepository;
import net.luismarquez.projects.MovieManagement.service.UserService;
import net.luismarquez.projects.MovieManagement.service.validator.PasswordValidator;
import net.luismarquez.projects.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Autowired
    private RatingCrudRepository ratingCrudRepository;

    @Autowired
    private MovieCrudRepository movieCrudRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<GetUser> findAll(String name, Pageable pageable) {
        Page<User> entities = userCrudRepository.findByNameContaining(name, pageable);
        return entities.map(UserMapper::toGetDto);
    }

    @Transactional(readOnly = true)
    @Override
    public GetUserStatistic findOneByUsername(String username) {

        int totalRatings = ratingCrudRepository.countByUserUsername(username);
        double averageRating = ratingCrudRepository.avgRatingByUsername(username);
        int lowestRating = ratingCrudRepository.minRatingByUsername(username);
        int highestRating = ratingCrudRepository.maxRatingByUsername(username);

        return UserMapper.toGetStatisticDto(this.findOneEntityByUsername(username), totalRatings, averageRating, lowestRating, highestRating);
    }

    @Transactional(readOnly = true)
    @Override
    public User findOneEntityByUsername(String username) {
        return userCrudRepository.findByUsername(username)
                .orElseThrow( () -> new ObjectNotFoundException("[user:" + username + "]"));
    }

    @Override
    public GetUser saveOne(SaveUser saveDto) {
        PasswordValidator.validatePassword(saveDto.password(), saveDto.passwordRepeated());

        User newUser = UserMapper.toEntity(saveDto);
        return UserMapper.toGetDto(userCrudRepository.save(newUser));
    }

    @Override
    public GetUser updateOneByUsername(String username, SaveUser saveDto) {
        PasswordValidator.validatePassword(saveDto.password(), saveDto.passwordRepeated());

        User oldUser = this.findOneEntityByUsername(username);
        UserMapper.updateEntity(oldUser, saveDto);

        return UserMapper.toGetDto(userCrudRepository.save(oldUser));
    }

    @Override
    public void deleteOneByUsername(String username) {
//        User user = this.findOneByUsername(username);
//        userCrudRepository.delete(user);

        if(userCrudRepository.deleteByUsername(username) != 1){
            throw new ObjectNotFoundException("[user:" + username + "]");
        }
    }

}
