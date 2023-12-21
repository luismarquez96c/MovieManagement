package net.luismarquez.projects.MovieManagement.service.impl;

import net.luismarquez.projects.MovieManagement.exception.ObjectNotFoundException;
import net.luismarquez.projects.MovieManagement.persistence.entity.Movie;
import net.luismarquez.projects.MovieManagement.persistence.entity.User;
import net.luismarquez.projects.MovieManagement.persistence.repository.MovieCrudRepository;
import net.luismarquez.projects.MovieManagement.persistence.repository.UserCrudRepository;
import net.luismarquez.projects.MovieManagement.service.UserService;
import net.luismarquez.projects.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<User> findAll() {
        return userCrudRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAllByName(String name) {
        return userCrudRepository.findByNameContaining(name);
    }

    @Transactional(readOnly = true)
    @Override
    public User findOneByUsername(String username) {
        return userCrudRepository.findByUsername(username)
                .orElseThrow( () -> new ObjectNotFoundException("[user:" + username + "]"));
    }

    @Override
    public User saveOne(User user) {
        return userCrudRepository.save(user);
    }

    @Override
    public User updateOneByUsername(String username, User user) {
        User oldUser = this.findOneByUsername(username);
        oldUser.setName(user.getName());
        oldUser.setPassword(user.getPassword());

        return userCrudRepository.save(oldUser);
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
