package net.luismarquez.projects.MovieManagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.luismarquez.projects.MovieManagement.dto.request.SaveUser;
import net.luismarquez.projects.MovieManagement.dto.response.GetUser;
import net.luismarquez.projects.MovieManagement.exception.ObjectNotFoundException;
import net.luismarquez.projects.MovieManagement.persistence.entity.User;
import net.luismarquez.projects.MovieManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<GetUser>> findAll(@RequestParam(required = false) String name){

        List<GetUser> users = null;

        if(StringUtils.hasText(name)){
            users = userService.findAllByName(name);
        }else{
            users = userService.findAll();
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{user}")
    public ResponseEntity<GetUser> findOneByUsername(@PathVariable("user") String username ){
        return ResponseEntity.ok(userService.findOneByUsername(username));
    }

    @PostMapping
    public ResponseEntity<GetUser> createOne(@RequestBody @Valid SaveUser saveDto,
                                          HttpServletRequest request) throws UnsupportedEncodingException {

        GetUser createdUser = userService.saveOne(saveDto);
        String baseURL = request.getRequestURL().toString();
        URI newLocation = URI.create(baseURL + "/" + saveDto.username());

        return ResponseEntity.created(newLocation).body(createdUser);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<GetUser> updateOneByUsername(@PathVariable String username,
                                                    @RequestBody @Valid SaveUser saveDto){

        try{
            GetUser updatedUser = userService.updateOneByUsername(username, saveDto);
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
