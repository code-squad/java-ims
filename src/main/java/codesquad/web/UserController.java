package codesquad.web;

import codesquad.domain.Result;
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

import static codesquad.security.HttpSessionUtils.USER_SESSION_KEY;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "userService")
    private UserService userService;

    @PostMapping
    public String create(UserDto userDto) {
        userService.add(userDto);
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(UserDto userDto, HttpSession session, Model model) {
        Result result = userService.login(userDto);
        if (!result.isValid()) {
            model.addAttribute("errMessage", result.get());
            return "/user/loginFail";
        }
        session.setAttribute(USER_SESSION_KEY, result.get());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (HttpSessionUtils.isLoginUser(session)) {
            session.removeAttribute(USER_SESSION_KEY);
        }
        return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        log.debug("LoginUser : {}", loginUser);
        model.addAttribute("user", userService.findById(loginUser, id));
        return "/user/edit";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, UserDto target) {
        userService.update(loginUser, id, target);
        return "redirect:/users";
    }

}
