package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.domain.User;
import codesquad.dto.UserDto;
import codesquad.security.HttpSessionUtils;
import codesquad.security.LoginUser;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> create(@Valid @RequestBody UserDto user) {
        User savedUser = userService.add(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/users/" + savedUser.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public UserDto show(@LoginUser User loginUser, @PathVariable long id) {
        User user = userService.findById(id);
        return user._toUserDto();
    }

    @PutMapping("/{id}")
    public void update(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody UserDto updatedUser) {
        userService.update(loginUser, id, updatedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(String userId, String password, HttpSession session) throws UnAuthenticationException {
        User loginUser = userService.login(userId, password);
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, loginUser);
        log.debug("session~~~~~ : {}", HttpSessionUtils.isLoginUser(session));
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<User>(loginUser, headers, HttpStatus.ACCEPTED);
    }
}
