package codesquad.service;

import codesquad.InvalidLoginInfoException;
import codesquad.UnAuthorizedException;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.UserDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Service
public class UserService {

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    public User add(UserDto userDto) {
        return userRepository.save(userDto._toUser());
    }

    public User update(User loginUser, long id, @Valid UserDto updatedUser) {
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

    public User login(String userId, String password) throws InvalidLoginInfoException {
        return userRepository.findByUserId(userId).filter(user -> user.matchPassword(password)).orElseThrow(InvalidLoginInfoException::new);
    }
}
