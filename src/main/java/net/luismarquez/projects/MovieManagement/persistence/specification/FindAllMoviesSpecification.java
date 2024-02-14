package net.luismarquez.projects.MovieManagement.persistence.specification;

import jakarta.persistence.criteria.*;
import net.luismarquez.projects.MovieManagement.dto.request.MovieSearchCriteria;
import net.luismarquez.projects.MovieManagement.persistence.entity.Movie;
import net.luismarquez.projects.MovieManagement.persistence.entity.Rating;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FindAllMoviesSpecification implements Specification<Movie> {

    private MovieSearchCriteria searchCriteria;

    public FindAllMoviesSpecification(MovieSearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        //root = from Movie
        //query = criterios de la consulta en si misma
        //criteriaBuilder = fabrica que te permite construir predicados y expresiones

        List<Predicate> predicatesWithAnd = new ArrayList<>();

        if(StringUtils.hasText(this.searchCriteria.title())){
            Predicate titleLike = criteriaBuilder.like(root.get("title"), "%" + this.searchCriteria.title() + "%");
            //m.title like '%asdasdasd%'
            predicatesWithAnd.add(titleLike);
        }

        if(this.searchCriteria.genre() != null){
            Predicate genreEqual = criteriaBuilder.equal(root.get("genre"), this.searchCriteria.genre());
            //m.genre = ?
            predicatesWithAnd.add(genreEqual);
        }

        if(this.searchCriteria.minReleaseYear() != null && this.searchCriteria.minReleaseYear().intValue() > 0){
            Predicate releaseYearGreaterThanEqual = criteriaBuilder.greaterThanOrEqualTo(root.get("releaseYear"), this.searchCriteria.minReleaseYear());
            // m.releaseYear >= ?
            predicatesWithAnd.add(releaseYearGreaterThanEqual);
        }

        if(this.searchCriteria.maxReleaseYear() != null && this.searchCriteria.maxReleaseYear() > 0){
            Predicate releaseYearLessThanEqual = criteriaBuilder.lessThanOrEqualTo(root.get("releaseYear"), this.searchCriteria.maxReleaseYear());
            //m.releaseYear <= ?
            predicatesWithAnd.add(releaseYearLessThanEqual);
        }

        if(this.searchCriteria.minAverageRating() != null && this.searchCriteria.minAverageRating() > 0){
            Subquery<Double> averageRatingSubquery = getAverageRatingSubquery(root, query, criteriaBuilder);

            Predicate averageRatingGreaterThanEqual = criteriaBuilder.greaterThanOrEqualTo(averageRatingSubquery, this.searchCriteria.minAverageRating().doubleValue());
            predicatesWithAnd.add(averageRatingGreaterThanEqual);
        }

        return criteriaBuilder.and(predicatesWithAnd.toArray(new Predicate[0]));

        // select m.*
        // from movie m
        // where 1 = 1  and
        //              (m.title like '%?1%'
        //              and m.genre = ?2
        //              and m.releaseYear >= ?3
        //              and m.releaseYear <= ?4)
        //              or (select avg(r1_0.rating)  from rating r1_0 where r1_0.movie_id = m1_0.id)
        //                      >= searchCriteria.minAverageRating()


    }

    private static Subquery<Double> getAverageRatingSubquery(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Subquery<Double> averageRatingSubquery = query.subquery(Double.class);// select avg(rating)
        Root<Rating> ratingRoot = averageRatingSubquery.from(Rating.class);//from rating

        averageRatingSubquery.select( criteriaBuilder.avg(ratingRoot.get("rating")) );//avg(r1_0.rating)

        Predicate movieIdEqual = criteriaBuilder.equal(root.get("id"), ratingRoot.get("movieId"));
        averageRatingSubquery.where(movieIdEqual);

        return averageRatingSubquery;
    }

    private Subquery<Double> getAverageRatingSubQuery() {
        return null;
    }
}
