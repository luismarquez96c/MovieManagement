package net.luismarquez.projects.MovieManagement.controller;

import net.luismarquez.projects.MovieManagement.exception.ObjectNotFoundException;
import net.luismarquez.projects.MovieManagement.persistence.entity.User;
import net.luismarquez.projects.MovieManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> findAll(@RequestParam(required = false) String name){

        List<User> users = null;

        if(StringUtils.hasText(name)){
            users = userService.findAllByName(name);
        }else{
            users = userService.findAll();
        }

        return ResponseEntity.ok(users);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{user}")
    public ResponseEntity<User> findOneByUsername(@PathVariable("user") String username ){
        try{
            return ResponseEntity.ok(userService.findOneByUsername(username));
        }catch (ObjectNotFoundException exception){
//            return ResponseEntity.status(404).build();
            return ResponseEntity.notFound().build();
        }
    }

}
