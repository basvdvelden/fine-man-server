package nl.management.finance.user;

import nl.management.finance.AuthContext;
import nl.management.finance.exceptions.PinVerificationFailedException;
import nl.management.finance.exceptions.PinNotRegisteredException;
import nl.management.finance.user.dao.UserRepository;
import nl.management.finance.user.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthContext context;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder, AuthContext context) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.context = context;
    }

    public void verifyPin(UUID uuid, PinDto dto) {
        User user = userRepository.findByUserId(uuid);
        if (user == null) {
            LOG.warn("No user pin could be found for uuid: {}", uuid.toString());
            throw new PinNotRegisteredException("No user pin could be found for this uuid");
        }
        if (!encoder.matches(dto.getPin(), user.getPin())) {
            LOG.warn("Pin does not match uuid: {}", uuid.toString());
            throw new PinVerificationFailedException("Pin code did not match!");
        }
    }

    @Transactional
    public void register(UUID uuid, RegisterDto dto) {
        User user = userRepository.findByUserId(uuid);
        if (user != null) {
            LOG.warn(String.format("already registered, user=%s", uuid.toString()));
            return;
        }
        user = new User();
        user.setUserId(uuid);
        user.setPin(encoder.encode(dto.getPin()));
        userRepository.save(user);
    }

    public User getUser(UUID userId) {
        LOG.error("fjklasdhfjklasdh");
        return userRepository.findByUserId(userId);
    }
}
