package net.luismarquez.projects.MovieManagement.persistence.repository;

import net.luismarquez.projects.MovieManagement.persistence.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MovieCrudRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
//    @Query("select count(r.rating) from Movie m join m.ratings r where m.id = ?1")
//    int countRatingsById(Long id);
//
//    @Query("select max(r.rating) from Movie m join m.ratings r where m.id = ?1")
//    int maxRatingById(Long id);
}
