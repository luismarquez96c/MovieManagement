package net.luismarquez.projects.MovieManagement.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import net.luismarquez.projects.MovieManagement.util.MovieGenre;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record SaveMovie(
        @Size(min = 4, max = 255, message = "{generic.size}")
        @NotBlank(message = "{generic.notblank}")
        String title,
        @Size(min = 4, max = 255, message = "{generic.size}")
        @NotBlank(message = "{generic.notblank}")
        String director,
        MovieGenre genre,
        @Min(value = 1900, message = "{generic.min}")
        @JsonProperty(value = "release_year") int releaseYear
//        @JsonProperty("availability_end_time")
//        @JsonFormat(pattern = "yyyy-MM-dd")
//        @FutureOrPresent
//        LocalDate availabilityEndTime,
) implements Serializable { }
