package nl.management.finance.user;

import nl.management.finance.user.models.PinDto;
import nl.management.finance.user.models.RegisterDto;
import nl.management.finance.user.models.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    @Autowired
    public UserController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable("userId") UUID userId) {
        return mapper.toDto(service.getUser(userId));
    }

    @PostMapping("/{uuid}/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@PathVariable("uuid") UUID uuid, @RequestBody RegisterDto dto) {
        service.register(uuid, dto);
    }

    @PostMapping("/{uuid}/pin/verify")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void verifyPin(@PathVariable("uuid") UUID uuid, PinDto dto) {
        service.verifyPin(uuid, dto);
    }
}
