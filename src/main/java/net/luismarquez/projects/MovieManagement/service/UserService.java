package net.luismarquez.projects.MovieManagement.service;

import net.luismarquez.projects.MovieManagement.dto.request.SaveUser;
import net.luismarquez.projects.MovieManagement.dto.response.GetUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<GetUser> findAll(String name, Pageable pageable);
    GetUser findOneByUsername(String username);
    GetUser saveOne(SaveUser saveDto);
    GetUser updateOneByUsername(String username, SaveUser saveDto);
    void deleteOneByUsername(String username);
}
