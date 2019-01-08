package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.UserDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class UserService {
    @Value("${error.not.supported.id}")
    private String idErrorMessage;

    @Value("${error.not.supported.password}")
    private String passwordErrorMessage;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    private static final Logger logger = getLogger(UserService.class);

    public User add(UserDto userDto) {
        logger.debug("UserDto : {}", userDto.toString());
        return userRepository.save(userDto._toUser());
    }

    public User update(User loginUser, long id, UserDto updatedUser) {
        logger.debug("Call UserService update Method() UserDto : {}", updatedUser.toString());
        User original = findById(loginUser, id);
        original.update(loginUser, updatedUser._toUser());
        return userRepository.save(original);
    }

    public User findById(User loginUser, long id) {
        return userRepository.findById(id)
                .filter(user -> user.equals(loginUser))
                .orElseThrow(UnAuthorizedException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User login(String userId, String password) throws UnAuthenticationException {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (!maybeUser.isPresent()) {
            throw new UnAuthenticationException(idErrorMessage);
        }

        User user = maybeUser.get();
        if (!user.matchPassword(password)) {
            throw new UnAuthenticationException(passwordErrorMessage);
        }

        return user;
    }
}
