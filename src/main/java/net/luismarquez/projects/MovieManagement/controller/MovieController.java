package net.luismarquez.projects.MovieManagement.controller;

import net.luismarquez.projects.MovieManagement.persistence.entity.Movie;
import net.luismarquez.projects.MovieManagement.service.MovieService;
import net.luismarquez.projects.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Movie> findAll(@RequestParam(required = false) String title,
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

        return peliculas;
    }

//        @RequestMapping(method = RequestMethod.GET, params = {"title", "genre"})
//        public List<Movie> findAllByGenreAndTitle(@RequestParam String title,
//                                                @RequestParam MovieGenre genre){
//            System.out.println("Metodo: findAllByGenreAndTitle");
//            return movieService.findAllByGenreAndTitle(genre, title);
//        }
//
//        @RequestMapping(method = RequestMethod.GET, params = "title")
//        public List<Movie> findAllByTitle(@RequestParam String title){
//            System.out.println("Metodo: findAllByTitle");
//            return movieService.findAllByTitle(title);
//        }
//
//        @RequestMapping(method = RequestMethod.GET, params = "genre")
//        public List<Movie> findAllByGenre(@RequestParam MovieGenre genre){
//            System.out.println("Metodo: findAllByGenre");
//            return movieService.findAllByGenre(genre);
//        }
//
//        @RequestMapping(method = RequestMethod.GET, params = {"!title","!genre"})
//        public List<Movie> findAll(){
//            System.out.println("Metodo: findAll");
//            return movieService.findAll();
//        }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Movie findOneById(@PathVariable Long id){
        return movieService.findOneById(id);
    }

}
