package net.luismarquez.projects.MovieManagement.service;

import net.luismarquez.projects.MovieManagement.dto.request.SaveUser;
import net.luismarquez.projects.MovieManagement.dto.response.GetUser;
import net.luismarquez.projects.MovieManagement.dto.response.GetUserStatistic;
import net.luismarquez.projects.MovieManagement.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<GetUser> findAll(String name, Pageable pageable);

    /**
     * @param username
     * @throws {@link net.luismarquez.projects.MovieManagement.exception.ObjectNotFoundException} if the given username do not exist
     * @return
     */
    GetUserStatistic findOneByUsername(String username);

    /**
     * @param username
     * @throws {@link net.luismarquez.projects.MovieManagement.exception.ObjectNotFoundException} if the given username do not exist
     * @return
     */
    User findOneEntityByUsername(String username);
    GetUser saveOne(SaveUser saveDto);
    GetUser updateOneByUsername(String username, SaveUser saveDto);
    void deleteOneByUsername(String username);
}
