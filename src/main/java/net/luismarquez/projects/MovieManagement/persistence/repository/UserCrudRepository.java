package net.luismarquez.projects.MovieManagement.persistence.repository;

import jakarta.persistence.criteria.Predicate;
import net.luismarquez.projects.MovieManagement.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

public interface UserCrudRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    default Page<User> findByNameContaining(String name, Pageable pageable){
        Specification<User> userSpecification = (root, query, builder) -> {
            if(StringUtils.hasText(name)){
                Predicate nameLike = builder.like(root.get("name"), "%" + name + "%");
                return nameLike;
            }

            return null;
        };

        return this.findAll(userSpecification, pageable);
    }

    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional
    int deleteByUsername(String username);

}
