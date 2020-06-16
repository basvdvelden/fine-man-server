package nl.management.finance.user.models;

import java.util.UUID;

public class UserDto {
    private UUID userId;

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}
