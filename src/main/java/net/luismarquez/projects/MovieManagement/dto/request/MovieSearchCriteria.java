package net.luismarquez.projects.MovieManagement.dto.request;

import net.luismarquez.projects.MovieManagement.util.MovieGenre;

public record MovieSearchCriteria(
        String title,
        MovieGenre genre,
        Integer minReleaseYear,
        Integer maxReleaseYear,
        Integer minAverageRating
) {
}
