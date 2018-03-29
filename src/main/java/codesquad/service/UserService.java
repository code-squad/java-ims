package codesquad.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.UserDto;

@Service
public class UserService {

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    public User add(UserDto userDto) {
        return userRepository.save(userDto._toUser());
    }

    public User update(User loginUser, long id, UserDto updatedUser) {
        User original = userRepository.findOne(id);
        original.update(loginUser, updatedUser._toUser());
        return userRepository.save(original);
    }

    public User findById(User loginUser, long id) {
        User user = userRepository.findOne(id);
        if (!user.equals(loginUser)) {
            throw new UnAuthorizedException();
        }
        return user;
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

    @Transactional
	public void addAssignee(User loginUser, Issue issue, long userId) {
		issue.setAssignee(findById(loginUser, userId));
	}
}
