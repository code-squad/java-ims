package codesquad.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.UserDto;
import codesquad.web.UserController;

@Service
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Resource(name = "userRepository")
	private UserRepository userRepository;

	public User add(UserDto userDto) {
		return userRepository.save(userDto._toUser());
	}

	@Transactional
	public User update(User loginUser, long id, UserDto updatedUser) {
		User original = userRepository.findOne(id);
		return original.update(loginUser, updatedUser._toUser());
	}

	public User findById(User loginUser, long id) {
		log.debug("Userservice findeById systemIn");
		User user = userRepository.findOne(id);
		log.debug("-=-user is " + user);
		return Optional.of(userRepository.findOne(id)).orElseThrow(UnAuthorizedException::new);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User login(String userId, String password) throws UnAuthenticationException {
		User user = userRepository.findByUserId(userId).orElseThrow(UnAuthorizedException::new);
		log.debug("comming user is " + user.toString());
		if (!user.matchPassword(password)) {
			throw new UnAuthenticationException();
		}

		return user;
	}
}
