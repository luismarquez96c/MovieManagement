package net.luismarquez.projects.MovieManagement.mapper;

import net.luismarquez.projects.MovieManagement.dto.request.SaveUser;
import net.luismarquez.projects.MovieManagement.dto.response.GetUser;
import net.luismarquez.projects.MovieManagement.persistence.entity.User;

import java.util.List;

public class UserMapper {

    public static GetUser toGetDto(User entity){
        if(entity == null) return null;

        return new GetUser(
                entity.getUsername(),
                entity.getName(),
                RatingMapper.toGetUserRatingDtoList(entity.getRatings())
        );
    }

    public static List<GetUser> toGetDtoList(List<User> entities){
        if(entities == null) return null;

        return entities.stream()
                .map(UserMapper::toGetDto)
                .toList();
    }

    public static User toEntity(SaveUser saveDto){
        if(saveDto == null) return null;

        User newUser = new User();
        newUser.setUsername(saveDto.username());
        newUser.setName(saveDto.name());
        newUser.setPassword(saveDto.password());

        return newUser;
    }

    public static void updateEntity(User oldUser, SaveUser saveDto) {
        if(oldUser == null || saveDto == null) return;

        oldUser.setName(saveDto.name());
        oldUser.setPassword(saveDto.password());
    }
}
