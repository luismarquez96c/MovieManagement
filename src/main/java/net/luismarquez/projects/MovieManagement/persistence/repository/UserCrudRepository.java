package net.luismarquez.projects.MovieManagement.persistence.repository;

import net.luismarquez.projects.MovieManagement.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface UserCrudRepository extends JpaRepository<User, Long> {

    List<User> findByNameContaining(String name);

    Optional<User> findByUsername(String username);

    @Modifying
    int deleteByUsername(String username);

}
