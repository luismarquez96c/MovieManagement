package net.luismarquez.projects.MovieManagement.service;

import net.luismarquez.projects.MovieManagement.persistence.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    List<User> findAllByName(String name);
    User findOneByUsername(String username);
    User saveOne(User user);
    User updateOneByUsername(String username, User user);
    void deleteOneByUsername(String username);
}
