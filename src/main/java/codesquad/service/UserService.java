package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.UserDto;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class UserService {
    private static final Logger log = getLogger(UserService.class);

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    public User create(User user) {
        log.debug("user:{}",user);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public User update(User loginUser, long id, UserDto updatingUser) {
        return findById(id).updateUser(loginUser, updatingUser._toUser());
    }

    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User login(String userId, String password) throws UnAuthenticationException {
        return userRepository.findByUserId(userId)
                .filter(userDto -> userDto.matchPassword(password))
                .orElseThrow(UnAuthenticationException::new);
    }
}
