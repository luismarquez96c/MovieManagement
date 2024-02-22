package net.luismarquez.projects.MovieManagement.exception;

public class DuplicateRatingException extends RuntimeException {

    private String username;
    private Long movieId;

    public DuplicateRatingException(String username, Long movieId) {
        this.username = username;
        this.movieId = movieId;
    }

    @Override
    public String getMessage() {
        return String.format("Rating already submitted for movie with ID %d by user %s. Only one rating per user per movie is allowed.", this.movieId, this.username);
    }
}
