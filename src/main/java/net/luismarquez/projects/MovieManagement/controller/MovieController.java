package net.luismarquez.projects.MovieManagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.luismarquez.projects.MovieManagement.dto.request.SaveMovie;
import net.luismarquez.projects.MovieManagement.dto.response.ApiError;
import net.luismarquez.projects.MovieManagement.dto.response.GetMovie;
import net.luismarquez.projects.MovieManagement.exception.InvalidPasswordException;
import net.luismarquez.projects.MovieManagement.exception.ObjectNotFoundException;
import net.luismarquez.projects.MovieManagement.persistence.entity.Movie;
import net.luismarquez.projects.MovieManagement.service.MovieService;
import net.luismarquez.projects.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @GetMapping
    public ResponseEntity<List<GetMovie>> findAll(@RequestParam(required = false) String title,
                                                  @RequestParam(required = false) MovieGenre genre){

        List<GetMovie> movies = null;

        if(StringUtils.hasText(title) && genre != null ){
            movies = movieService.findAllByGenreAndTitle(genre, title);
        }else if(StringUtils.hasText(title)){
            movies = movieService.findAllByTitle(title);
        }else if(genre != null){
            movies = movieService.findAllByGenre(genre);
        }else{
            movies = movieService.findAll();
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GetMovie> findOneById(@PathVariable Long id){
        return ResponseEntity.ok(movieService.findOneById(id));
    }

    @PostMapping
    public ResponseEntity<GetMovie> createOne(@RequestBody @Valid SaveMovie saveDto,
                                           HttpServletRequest request){

//        System.out.println("Fecha: " + saveDto.availabilityEndTime());
        GetMovie movieCreated = movieService.createOne(saveDto);

        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + movieCreated.id());

        return ResponseEntity
                .created(newLocation)
                .body(movieCreated);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GetMovie> updateOneById(@PathVariable Long id,
                                                  @Valid @RequestBody SaveMovie saveDto){
        GetMovie updatedMovie = movieService.updateOneById(id, saveDto);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long id){
        movieService.deleteOneById(id);
        return ResponseEntity.noContent().build();
    }

}
