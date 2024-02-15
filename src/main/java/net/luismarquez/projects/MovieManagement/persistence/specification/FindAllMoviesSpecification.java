package net.luismarquez.projects.MovieManagement.persistence.specification;

import jakarta.persistence.criteria.*;
import net.luismarquez.projects.MovieManagement.dto.request.MovieSearchCriteria;
import net.luismarquez.projects.MovieManagement.persistence.entity.Movie;
import net.luismarquez.projects.MovieManagement.persistence.entity.Rating;
import net.luismarquez.projects.MovieManagement.util.MovieGenre;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
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

        List<Predicate> predicates = new ArrayList<>();

        if(this.searchCriteria.genres() != null && this.searchCriteria.genres().length > 0){

            List<Predicate> genrePredicates = new ArrayList<>();

            for(MovieGenre genre : this.searchCriteria.genres()){
                Predicate genreEqual = criteriaBuilder.equal(root.get("genre"), genre);
                genrePredicates.add(genreEqual);
            }

            Predicate genreEqual = criteriaBuilder.or(genrePredicates.toArray(new Predicate[0]));
            //and (m.genre = ?2.1 OR m.genre = ?2.2 OR m.genre = ?2.3 OR m.genre = ?2.4)
            predicates.add(genreEqual);
        }

        if(this.searchCriteria.minReleaseYear() != null && this.searchCriteria.minReleaseYear().intValue() > 0){
            Predicate releaseYearGreaterThanEqual = criteriaBuilder.greaterThanOrEqualTo(root.get("releaseYear"), this.searchCriteria.minReleaseYear());
            // m.releaseYear >= ?
            predicates.add(releaseYearGreaterThanEqual);
        }

        if(this.searchCriteria.maxReleaseYear() != null && this.searchCriteria.maxReleaseYear() > 0){
            Predicate releaseYearLessThanEqual = criteriaBuilder.lessThanOrEqualTo(root.get("releaseYear"), this.searchCriteria.maxReleaseYear());
            //m.releaseYear <= ?
            predicates.add(releaseYearLessThanEqual);
        }

        if(this.searchCriteria.minAverageRating() != null && this.searchCriteria.minAverageRating() > 0){
            Subquery<Double> averageRatingSubquery = getAverageRatingSubquery(root, query, criteriaBuilder);

            Predicate averageRatingGreaterThanEqual = criteriaBuilder.greaterThanOrEqualTo(averageRatingSubquery, this.searchCriteria.minAverageRating().doubleValue());
            predicates.add(averageRatingGreaterThanEqual);
        }

        Predicate predicatesWithAnd = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        if(StringUtils.hasText(this.searchCriteria.title())){
            Predicate titleLike = criteriaBuilder.like(root.get("title"), "%" + this.searchCriteria.title() + "%");
            //m.title like '%asdasdasd%'
            return criteriaBuilder.or(titleLike, predicatesWithAnd);
        }

        return predicatesWithAnd;

        // select m.*
        // from movie m
        // where 1 = 1  and
        //              m.title like '%?1%'
        //              OR (m.genre = ?2.1 or m.genre = ?2.2 or m.genre = ?2.3)
        //              and m.releaseYear >= ?3
        //              and m.releaseYear <= ?4
        //              and (select avg(r1_0.rating)  from rating r1_0 where r1_0.movie_id = m1_0.id)
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
