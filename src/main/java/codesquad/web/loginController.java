package codesquad.web;

import codesquad.exception.UnAuthenticationException;
import codesquad.domain.User;
import codesquad.dto.UserDto;
import codesquad.security.HttpSessionUtils;
import codesquad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class loginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(UserDto userDto, HttpSession session) {
        try {
            User loginUser = userService.login(userDto.getUserId(), userDto.getPassword());
            session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, loginUser);
        }catch (UnAuthenticationException e) {
            return "/user/login_failed";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }
}
