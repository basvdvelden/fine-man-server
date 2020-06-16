package nl.management.finance.user;

import nl.management.finance.user.models.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {

    public UserMapper() {}

    public UserDto toDto(User user) {
        UserDto result = new UserDto();
        result.setUserId(user.getUserId());
        return result;
    }

    public User toEntity(UUID userId) {
        User user = new User();
        user.setUserId(userId);
        return user;
    }

    public User toEntity(UserDto dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        return user;
    }

    public User fromId(UUID id) {
        User result = new User();
        result.setUserId(id);
        return result;
    }
}
