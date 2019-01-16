package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Attachment;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.UserDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class UserService {

    @Autowired
    private MessageSource messageSource;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    private static final Logger logger = getLogger(UserService.class);

    public User add(UserDto userDto, Attachment avatar) {
        Optional<User> user = userRepository.findByUserId(userDto.getUserId());
        if(user.isPresent()) {
            throw new UnAuthorizedException(
                    messageSource.getMessage("error.duplication", null, Locale.getDefault()));
        }

        return userRepository.save(userDto._toUser(avatar));
    }

    @Transactional
    public User update(User loginUser, long id, UserDto updatedUser, Attachment avatar) {
        User original = findById(loginUser, id);
        return original.update(loginUser, updatedUser._toUser(), avatar);
    }

    public User findById(User loginUser, long id) {
        return userRepository.findById(id)
                .filter(user -> user.equals(loginUser))
                .orElseThrow(UnAuthorizedException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User login(String userId, String password) throws UnAuthorizedException {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (!maybeUser.isPresent()) {
            throw new UnAuthorizedException(
                    messageSource.getMessage("error.not.supported.id", null, Locale.getDefault()));
        }

        User user = maybeUser.get();
        logger.debug("User : {}, PWD : {}", user, password);
        if (!user.matchPassword(password)) {
            throw new UnAuthorizedException(
                    messageSource.getMessage("error.not.supported.password", null, Locale.getDefault()));
        }

        return user;
    }
}
