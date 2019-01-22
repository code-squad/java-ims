package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.UserDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class UserService {

    private static final Logger log = getLogger(UserService.class);

//    @Value("${default.profile.image}")
//    private String defaultImage;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public User add(UserDto userDto) {
        log.debug("userDto add : {}" , userDto._toUser());
        return userRepository.save(userDto._toUser());
    }

    @Transactional
    public User update(User loginUser, long id, UserDto updatedUser) {
        User original = findById(id);
        original.update(loginUser, updatedUser._toUser());
        return userRepository.save(original);
    }

    public User findById(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(UnAuthorizedException::new);
    }

    public User findById(long id) {
        return userRepository.findById(id)
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
        log.debug("User : {}~~~~~~~", user.toString());
        log.debug("User : {}~~~~~~~{}", password, user.getPassword());
        if (!user.matchPassword(password)) {
            throw new UnAuthenticationException();
        }
        log.debug("User tes : {}", user.toString());
        return user;
    }

    @Transactional
    public Issue setAssginee(long issueId, long id) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(UnknownError::new);
        issue.setAssignee(userRepository.findById(id).orElseThrow(UnknownError::new));
        return issue;
    }
}
