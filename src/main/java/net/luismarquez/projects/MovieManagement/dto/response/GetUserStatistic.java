package net.luismarquez.projects.MovieManagement.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public record GetUserStatistic(
        String username,
        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt,
        @JsonProperty("total_ratings") int totalRatings,
        @JsonProperty("average_ratings")
        Double averageRating,
        @JsonProperty("lowest_rating") int lowestRating,
        @JsonProperty("highest_rating") int highestRating
) implements Serializable {

        @Override
        public Double averageRating() {
                return Double.parseDouble(String.format("%1.2f", averageRating));
        }
}
