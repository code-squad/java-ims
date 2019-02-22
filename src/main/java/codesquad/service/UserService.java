package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.UserDto;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class UserService {
    private static final Logger log = getLogger(UserService.class);

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    public User add(UserDto userDto) {
        return userRepository.save(userDto._toUser());
    }

    public User update(User loginUser, long id, UserDto updatedUser) {
        User original = findById(loginUser, id);
        original.update(loginUser, updatedUser._toUser());
        return userRepository.save(original);
    }

    public User findByUserId(String userId){
        log.debug("레파지토리 확인:{}", userRepository.findByUserId(userId).orElse(null));
        return userRepository.findByUserId(userId).orElse(null);
    }

    public User findByIda(long id){
        return userRepository.findById(id).orElse(null);
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
            throw new UnAuthenticationException();
        }

        User user = maybeUser.get();
        if (!user.matchPassword(password)) {
            throw new UnAuthenticationException();
        }

        return user;
    }
}
