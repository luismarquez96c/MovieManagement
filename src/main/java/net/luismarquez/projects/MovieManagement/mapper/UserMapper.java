package net.luismarquez.projects.MovieManagement.mapper;

import net.luismarquez.projects.MovieManagement.dto.request.SaveUser;
import net.luismarquez.projects.MovieManagement.dto.response.GetUser;
import net.luismarquez.projects.MovieManagement.dto.response.GetUserStatistic;
import net.luismarquez.projects.MovieManagement.persistence.entity.User;

import java.util.List;

public class UserMapper {

    public static GetUserStatistic toGetStatisticDto(User entity, int totalRatings,
                                                     double averageRating, int lowestRating,
                                                     int highestRating) {
        if(entity == null) return null;

        return new GetUserStatistic(
                entity.getUsername(),
                entity.getCreatedAt(),
                totalRatings,
                averageRating,
                lowestRating,
                highestRating
        );
    }

    public static GetUser toGetDto(User entity){
        if(entity == null) return null;

        int totalRatings = entity.getRatings() != null ? entity.getRatings().size() : 0;
        return new GetUser(
                entity.getUsername(),
                entity.getName(),
                totalRatings
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
