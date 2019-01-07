package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.domain.User;
import codesquad.dto.UserDto;
import codesquad.security.HttpSessionUtils;
import codesquad.security.LoginUser;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import support.domain.ErrorMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/user")
public class ApiUserController {

    @Resource(name = "userService")
    private UserService userService;

    @Value("${error.not.supported}")
    private String errorMessage;

    private static final Logger logger = getLogger(ApiUserController.class);

    @PostMapping("")
    public ResponseEntity<Void> create(@Valid @RequestBody UserDto user) {
        User savedUser = userService.add(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/user/" + savedUser.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public UserDto show(@LoginUser User loginUser, @PathVariable long id) {
        User user = userService.findById(loginUser, id);
        return user._toUserDto();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@LoginUser User loginUser, @PathVariable long id, @RequestBody @Valid UserDto updatedUser,
                                       BindingResult bindingResult, HttpSession httpSession) {
        if(bindingResult.hasErrors()) {
            logger.debug("잘못된 형식으로 입력하셨습니다.");
            return new ResponseEntity<ErrorMessage>(new ErrorMessage(errorMessage), HttpStatus.FORBIDDEN);
        }
        logger.debug("Call update Method() -> loginUser : {}, updatedUser : {}", loginUser, updatedUser);
        User user = userService.update(loginUser, id, updatedUser);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/"));
        HttpSessionUtils.updateUserSession(httpSession, updatedUser);
        return new ResponseEntity<User>(user, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid UserDto userDto, HttpSession httpSession) throws UnAuthenticationException {
        User user = userService.login(userDto.getUserId(), userDto.getPassword());
        HttpSessionUtils.setSession(httpSession, user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/"));
        return new ResponseEntity<User>(user, httpHeaders, HttpStatus.ACCEPTED);
    }
}
