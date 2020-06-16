package nl.management.finance.user.dao;

import nl.management.finance.userbank.UserBank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserBankRepository extends JpaRepository<UserBank, Integer> {
    List<UserBank> getByUserUserId(UUID userId);

    UserBank findByUserUserIdAndBankId(UUID userId, int bankId);
}
