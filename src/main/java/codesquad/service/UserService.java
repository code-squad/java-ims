package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.user.Avatar;
import codesquad.domain.user.User;
import codesquad.domain.user.UserRepository;
import codesquad.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "fileService")
    private FileService fileService;

    public User add(UserDto userDto, MultipartFile file) throws IOException {
        Avatar avatar = Avatar.of(file);
        if (!avatar.isDefaultAvatar()) fileService.upload(avatar, file);
        return userRepository.save(userDto._toUser(avatar));
    }

    @Transactional
    public User update(User loginUser, long id, UserDto updatedUser) {
        User original = findById(loginUser, id);
        original.update(loginUser, updatedUser._toUser(Avatar.DEFAULT_AVATAR));
        return userRepository.save(original);
    }

    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
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
        return userRepository.findByUserId(userId)
                .filter(user -> user.matchPassword(password))
                .orElseThrow(() -> new UnAuthenticationException("아이디 또는 비밀번호가 다릅니다."));
    }
}
