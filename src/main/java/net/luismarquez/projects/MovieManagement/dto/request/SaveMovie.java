package net.luismarquez.projects.MovieManagement.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.luismarquez.projects.MovieManagement.util.MovieGenre;

import java.io.Serializable;

public record SaveMovie(
        String title,
        String director,
        MovieGenre genre,
        @JsonProperty(value = "release_year") int releaseYear
) implements Serializable { }
