package nl.management.finance.user.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User {

    @Column(nullable = false)
    private String pin;

    @Id
    @Column(unique = true, nullable = false)
    @Type(type="uuid-char")
    private UUID userId;

    public User() {}

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "pin='" + pin + '\'' +
                ", userId=" + userId +
                '}';
    }
}
