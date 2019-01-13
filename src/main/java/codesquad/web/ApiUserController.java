package codesquad.web;

import codesquad.domain.Attachment;
import codesquad.domain.User;
import codesquad.dto.UserDto;
import codesquad.security.HttpSessionUtils;
import codesquad.security.LoginUser;
import codesquad.service.AvatarService;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import support.domain.ErrorMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/user")
public class ApiUserController {

    @Resource(name = "userService")
    private UserService userService;

    @Autowired
    private AvatarService attachmentService;

    @Value("${error.not.supported}")
    private String errorMessage;

    private static final Logger logger = getLogger(ApiUserController.class);

    @PostMapping(consumes = {"multipart/form-data"}, value = "")
    public ResponseEntity create(@RequestPart("user") @Valid UserDto user,
                                 @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        Attachment avatar = attachmentService.createAvatar(file);
        User savedUser = userService.add(user, avatar);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/user/" + savedUser.getId()));
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public UserDto show(@LoginUser User loginUser, @PathVariable long id) {
        User user = userService.findById(loginUser, id);
        return user._toUserDto();
    }

    @PutMapping(consumes = {"multipart/form-data"}, value = "/{id}")
    public ResponseEntity update(@LoginUser User loginUser, @PathVariable long id, @RequestPart("user") @Valid UserDto updatedUser,
                                       BindingResult bindingResult, @RequestPart(value = "file", required = false) MultipartFile file, HttpSession httpSession) throws IOException {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity(new ErrorMessage(errorMessage), HttpStatus.FORBIDDEN);
        }
        Attachment avatar = attachmentService.updateAvatar(file);
        User user = userService.update(loginUser, id, updatedUser, avatar);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/"));
        HttpSessionUtils.updateUserSession(httpSession, updatedUser);
        return new ResponseEntity<User>(user, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserDto userDto, HttpSession httpSession) {
        logger.debug("UserDto : {}!", userDto);
        User user = userService.login(userDto.getUserId(), userDto.getPassword());
        HttpSessionUtils.setSession(httpSession, user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/"));
        return new ResponseEntity(user, httpHeaders, HttpStatus.ACCEPTED);
    }
}
