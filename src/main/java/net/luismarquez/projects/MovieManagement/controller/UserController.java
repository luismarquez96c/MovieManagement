package net.luismarquez.projects.MovieManagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.luismarquez.projects.MovieManagement.dto.request.SaveUser;
import net.luismarquez.projects.MovieManagement.dto.response.GetUser;
import net.luismarquez.projects.MovieManagement.dto.response.GetUserStatistic;
import net.luismarquez.projects.MovieManagement.service.RatingService;
import net.luismarquez.projects.MovieManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<Page<GetUser>> findAll(@RequestParam(required = false) String name,
                                                 Pageable pageable){

        Page<GetUser> users = userService.findAll(name, pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{user}")
    public ResponseEntity<GetUserStatistic> findOneByUsername(@PathVariable("user") String username ){
        return ResponseEntity.ok(userService.findOneByUsername(username));
    }

    @GetMapping(value = "/{username}/ratings")
    public ResponseEntity<Page<GetUser.GetRating>> findAllRatingsForUserByUsername(@PathVariable String username, Pageable pageable){
        return ResponseEntity.ok(ratingService.findAllByUsername(username, pageable));
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
        GetUser updatedUser = userService.updateOneByUsername(username, saveDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping(value = "{username}")
    public ResponseEntity<Void> deleteOneByUsername(@PathVariable String username){
        userService.deleteOneByUsername(username);
        return ResponseEntity.noContent().build();
    }

}
