package net.luismarquez.projects.MovieManagement.service.impl;

import net.luismarquez.projects.MovieManagement.dto.request.SaveUser;
import net.luismarquez.projects.MovieManagement.dto.response.GetUser;
import net.luismarquez.projects.MovieManagement.exception.ObjectNotFoundException;
import net.luismarquez.projects.MovieManagement.mapper.UserMapper;
import net.luismarquez.projects.MovieManagement.persistence.entity.Movie;
import net.luismarquez.projects.MovieManagement.persistence.entity.User;
import net.luismarquez.projects.MovieManagement.persistence.repository.MovieCrudRepository;
import net.luismarquez.projects.MovieManagement.persistence.repository.UserCrudRepository;
import net.luismarquez.projects.MovieManagement.service.UserService;
import net.luismarquez.projects.MovieManagement.service.validator.PasswordValidator;
import net.luismarquez.projects.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Autowired
    private MovieCrudRepository movieCrudRepository;

    @Transactional(readOnly = true)
    @Override
    public List<GetUser> findAll() {
        List<User> entites = userCrudRepository.findAll();
        return UserMapper.toGetDtoList(entites);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetUser> findAllByName(String name) {
        List<User> entities = userCrudRepository.findByNameContaining(name);
        return UserMapper.toGetDtoList(entities);
    }

    @Transactional(readOnly = true)
    @Override
    public GetUser findOneByUsername(String username) {
        return UserMapper.toGetDto(this.findOneEntityByUsername(username));
    }

    @Transactional(readOnly = true)
    private User findOneEntityByUsername(String username) {
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
