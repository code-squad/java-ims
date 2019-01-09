package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.domain.user.User;
import codesquad.dto.UserDto;
import codesquad.security.LoginUser;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static codesquad.security.HttpSessionUtils.USER_SESSION_KEY;

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
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm() {
        log.debug("로그인 폼");
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession httpSession) throws UnAuthenticationException {
        User loginUser = userService.login(userId, password);
        httpSession.setAttribute(USER_SESSION_KEY, loginUser);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        log.debug("LoginUser : {}", loginUser);
        model.addAttribute("user", userService.findById(loginUser, id));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, UserDto target, HttpSession httpSession) {
        log.debug("업데이트 유저 : " + target);
        userService.update(loginUser, id, target);
        httpSession.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }

}
