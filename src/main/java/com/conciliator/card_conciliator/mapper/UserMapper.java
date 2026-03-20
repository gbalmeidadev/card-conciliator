package com.conciliator.card_conciliator.mapper;

import com.conciliator.card_conciliator.dto.UserResponseDTO;
import com.conciliator.card_conciliator.entity.Profile;
import com.conciliator.card_conciliator.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getActive(),
                user.getCreatedAt(),
                user.getPerfis()
                        .stream()
                        .map(Profile::getName)
                        .collect(java.util.stream.Collectors.toSet())
        );
    }
}
