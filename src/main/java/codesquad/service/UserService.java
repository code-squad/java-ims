package codesquad.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.UserDto;

@Service
public class UserService {
	// autowired 랑 비슷한 역할.
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

	public User findById(long id) {
		return userRepository.findOne(id);
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

	public void register(User loginUser, Issue issue, User user) {
		if (!issue.isSameUser(loginUser)) {
			throw new UnAuthorizedException();
		}
		user.addIssue(issue);
		userRepository.save(user);
	}
}
