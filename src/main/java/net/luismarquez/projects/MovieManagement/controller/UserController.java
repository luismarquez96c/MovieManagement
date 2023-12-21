package net.luismarquez.projects.MovieManagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import net.luismarquez.projects.MovieManagement.exception.ObjectNotFoundException;
import net.luismarquez.projects.MovieManagement.persistence.entity.User;
import net.luismarquez.projects.MovieManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll(@RequestParam(required = false) String name){

        List<User> users = null;

        if(StringUtils.hasText(name)){
            users = userService.findAllByName(name);
        }else{
            users = userService.findAll();
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{user}")
    public ResponseEntity<User> findOneByUsername(@PathVariable("user") String username ){
        try{
            return ResponseEntity.ok(userService.findOneByUsername(username));
        }catch (ObjectNotFoundException exception){
//            return ResponseEntity.status(404).build();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createOne(@RequestBody User user,
                                          HttpServletRequest request){

        User createdUser = userService.saveOne(user);
        String baseURL = request.getRequestURL().toString();
        URI newLocation = URI.create(baseURL + "/" + user.getUsername());

        return ResponseEntity.created(newLocation).body(createdUser);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<User> updateOneByUsername(@PathVariable String username,
                                                    @RequestBody User user){

        try{
            User updatedUser = userService.updateOneByUsername(username, user);
            return ResponseEntity.ok(updatedUser);
        }catch (ObjectNotFoundException exception){
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping(value = "{username}")
    public ResponseEntity<Void> deleteOneByUsername(@PathVariable String username){
        try{
            userService.deleteOneByUsername(username);
            return ResponseEntity.noContent().build();
        }catch (ObjectNotFoundException exception){
            return ResponseEntity.notFound().build();
        }
    }

}
