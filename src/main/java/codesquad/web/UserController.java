package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.domain.User;
import codesquad.dto.UserDto;
import codesquad.security.HttpSessionUtils;
import codesquad.security.LoginUser;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "userService")
    private UserService userService;

    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }

    @PostMapping("")
    public String create(UserDto userDto) {
        userService.add(userDto);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list() {
        return "/index";
    }

    @GetMapping("/login/form")
    public String loginForm(){
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        try {
            User user = userService.login(userId, password);
            session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
            return "redirect:/users";
        } catch (UnAuthenticationException e) {
            return "user/login_failed";
        }
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        log.debug("LoginUser : {}", loginUser);
        model.addAttribute("user", userService.findById(loginUser, id));
        return "/user/updateForm";
    }



    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, UserDto target) {
        log.debug("!!!!!!!!!!!!!!!!! : {}",target);
        userService.update(loginUser, id, target);
        return "redirect:/users";
    }

}
