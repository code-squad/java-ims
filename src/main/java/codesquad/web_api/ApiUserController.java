package codesquad.web_api;

import codesquad.domain.User;
import codesquad.dto.UserDto;
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

import static codesquad.security.HttpSessionUtils.USER_SESSION_KEY;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
    private static final Logger log = getLogger(ApiUserController.class);

    @Resource(name = "userService")
    private UserService userService;

    @PostMapping("")        // 1.데이터 만들어서
    public ResponseEntity<Void> create(@Valid @RequestBody UserDto user) {
        User savedUser = userService.create(user._toUser());
        log.debug("user : {}", user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/users/" + savedUser.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping("{id}")         // 2. (1)에서 만든 데이터 가져오기
    public UserDto show(@LoginUser User loginUser, @PathVariable long id) {
        User user = userService.findById(id);
        return user._toUserDto();
    }

    @PutMapping("{id}")
    public UserDto update(@LoginUser User loginUser, @PathVariable long id
            , @Valid @RequestBody UserDto updatedUser) {
        return userService.update(loginUser, id, updatedUser)._toUserDto();
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(UserDto userDto, HttpSession httpSession) {
        User loginUser = userService.login(userDto.getUserId(), userDto.getPassword());
        httpSession.setAttribute(USER_SESSION_KEY, loginUser);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<User>(loginUser,headers, HttpStatus.ACCEPTED);
    }

}
