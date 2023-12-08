package net.luismarquez.projects.MovieManagement.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> findAll(@RequestParam(required = false) String title,
                                  @RequestParam(required = false) MovieGenre genre){

        List<Movie> peliculas = null;

        if(StringUtils.hasText(title) && genre != null ){
            peliculas = movieService.findAllByGenreAndTitle(genre, title);
        }else if(StringUtils.hasText(title)){
            peliculas = movieService.findAllByTitle(title);
        }else if(genre != null){
            peliculas = movieService.findAllByGenre(genre);
        }else{
            peliculas = movieService.findAll();
        }


//        HttpHeaders headers = new HttpHeaders();
//        return new ResponseEntity(peliculas, headers, HttpStatus.OK);//Opción 1
//        return ResponseEntity.status(HttpStatus.OK).body(peliculas);//Opción 2
        return ResponseEntity.ok(peliculas);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Movie> findOneById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(movieService.findOneById(id));
        }catch (ObjectNotFoundException exception){
            return ResponseEntity.notFound().build();
        }
    }

}
