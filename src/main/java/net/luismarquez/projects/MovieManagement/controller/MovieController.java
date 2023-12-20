package net.luismarquez.projects.MovieManagement.controller;

import jakarta.servlet.http.HttpServletRequest;
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

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @GetMapping
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<Movie> findOneById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(movieService.findOneById(id));
        }catch (ObjectNotFoundException exception){
            return ResponseEntity.notFound().build();
        }
    }

//    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Movie> createOneV1(@RequestParam String title,
                                           @RequestParam String director,
                                           @RequestParam MovieGenre genre,
                                           @RequestParam int releaseYear,
                                           HttpServletRequest request){

        Movie newMovie = new Movie();
        newMovie.setTitle(title);
        newMovie.setDirector(director);
        newMovie.setGenre(genre);
        newMovie.setReleaseYear(releaseYear);

        Movie movieCreated = movieService.createOne(newMovie);

        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + movieCreated.getId());

        return ResponseEntity
                .created(newLocation)
                .body(movieCreated);
    }

    @PostMapping
    public ResponseEntity<Movie> createOne(@RequestBody Movie newMovie,
                                           HttpServletRequest request){

        Movie movieCreated = movieService.createOne(newMovie);

        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + movieCreated.getId());

        return ResponseEntity
                .created(newLocation)
                .body(movieCreated);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Movie> updateOneById(@PathVariable Long id,
                                               @RequestBody Movie movie){
        try{
            Movie updatedMovie = movieService.updateOneById(id, movie);
            return ResponseEntity.ok(updatedMovie);
        }catch (ObjectNotFoundException exception){
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long id){

        try{
            movieService.deleteOneById(id);
            return ResponseEntity.noContent().build();
        }catch (ObjectNotFoundException exception){
            return ResponseEntity.notFound().build();
        }

    }

}
