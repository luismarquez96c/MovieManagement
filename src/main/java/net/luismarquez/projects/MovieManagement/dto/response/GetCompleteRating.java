package net.luismarquez.projects.MovieManagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record GetCompleteRating(
        @JsonProperty("rating_id") long ratingId,
        @JsonProperty("movie_id") long movieId,
        @JsonProperty("movie_title") String movieTitle,
        String username,
        int rating
) implements Serializable {
}
