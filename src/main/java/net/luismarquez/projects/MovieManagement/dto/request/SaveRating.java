package net.luismarquez.projects.MovieManagement.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

public record SaveRating(
        @JsonProperty("movie_id") @Positive(message = "{generic.positive}") Long movieId,
        @Pattern(regexp = "[a-zA-Z0-9-_]{8,255}", message = "{saveUser.username.pattern}")
        String username,
        @Min(value = 0, message = "{generic.min}") @Max(value = 5, message = "{generic.max}") Integer rating
) implements Serializable {
}
