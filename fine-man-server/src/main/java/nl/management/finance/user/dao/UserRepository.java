package nl.management.finance.user.dao;

import nl.management.finance.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(UUID uuid);
}
