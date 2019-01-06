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
    public ResponseEntity<Void> login(HttpSession session, @Valid @RequestBody UserDto userDto) throws UnAuthenticationException {
        log.debug("=#= login {}", userDto);
        User user = userService.login(userDto.getUserId(), userDto.getPassword());
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }





}
