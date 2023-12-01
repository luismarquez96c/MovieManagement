package net.luismarquez.projects.MovieManagement.controller;

import net.luismarquez.projects.MovieManagement.persistence.entity.User;
import net.luismarquez.projects.MovieManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> findAll(@RequestParam(required = false) String name){

        List<User> users = null;

        if(StringUtils.hasText(name)){
            users = userService.findAllByName(name);
        }else{
            users = userService.findAll();
        }

        return users;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{user}")
    public User findOneByUsername(@PathVariable("user") String username ){
        return userService.findOneByUsername(username);
    }

}
