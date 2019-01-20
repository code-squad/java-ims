package codesquad.web.api;

import codesquad.UnAuthenticationException;
import codesquad.domain.user.User;
import codesquad.dto.UserDto;
import codesquad.security.HttpSessionUtils;
import codesquad.security.LoginUser;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import support.domain.ErrorMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    private static final Logger log = getLogger(ApiUserController.class);

    @Resource(name = "userService")
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<User> create(@Valid @RequestBody UserDto user) {
        User savedUser = userService.add(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<User>(savedUser, headers, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public UserDto show(@LoginUser User loginUser, @PathVariable long id) {
        User user = userService.findById(loginUser, id);
        return user._toUserDto();
    }

    @PutMapping("{id}")
    public void update(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody UserDto updatedUser) {
        userService.update(loginUser, id, updatedUser);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody UserDto userDto, HttpSession session) {
        try {
            log.debug("userDto!!!!!!!!!! : {}", userDto);
            HttpHeaders headers = new HttpHeaders();
            User user = userService.login(userDto.getUserId(), userDto.getPassword());
            session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
            headers.setLocation(URI.create("/"));
            return new ResponseEntity<User>(user, headers, HttpStatus.OK);
        } catch (UnAuthenticationException e) {
            return new ResponseEntity<ErrorMessage>(new ErrorMessage("아이디 또는 비밀번호가 다릅니다."), HttpStatus.UNAUTHORIZED);
        }
    }
}
