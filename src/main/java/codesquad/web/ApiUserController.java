package codesquad.web;

import java.net.URI;

import javax.annotation.Resource;
import javax.validation.Valid;

import codesquad.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.domain.User;
import codesquad.dto.UserDto;
import codesquad.security.LoginUser;
import codesquad.service.UserService;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
    @Resource(name = "userService")
    private UserService userService;

    @Autowired
    IssueService issueService;

    @PostMapping("")
    public ResponseEntity<Void> create(@Valid @RequestBody UserDto user) {
        User savedUser = userService.add(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/users/" + savedUser.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public UserDto show(@LoginUser User loginUser, @PathVariable long id) {
        User user = userService.findById(loginUser, id);
        return user._toUserDto();
    }
    
    @PutMapping("{id}")
    public void update(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody UserDto updatedUser) {
        userService.update(loginUser, id, updatedUser);
    }

    @PostMapping("/{userId}/issues/{id}")
    public User addAssignee(@LoginUser User loginUser, @PathVariable long id, @PathVariable long userId) {
        return userService.addAssignee(loginUser, issueService.findById(id), userId);
    }
}
