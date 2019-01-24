package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.user.ProfileImage;
import codesquad.domain.user.User;
import codesquad.domain.user.UserRepository;
import codesquad.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "fileService")
    private FileService fileService;

    public User add(UserDto userDto, MultipartFile file) throws IOException {
        ProfileImage profileImage = ProfileImage.of(file);
        if (!profileImage.isDefaultImage()) {
            fileService.upload(profileImage, file);
        }
        return userRepository.save(userDto._toUser(profileImage));
    }

    public User update(User loginUser, long id, UserDto updatedUser) {
        User original = findByLoginId(loginUser, id);
        original.update(loginUser, updatedUser._toUser(ProfileImage.DEFAULT_IMAGE));
        return userRepository.save(original);
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(UnAuthorizedException::new);
    }

    public User findByLoginId(User loginUser, long id) {
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
