package codesquad.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import codesquad.UnAuthenticationException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.User;
import codesquad.dto.UserDto;
import codesquad.security.LoginUser;
import codesquad.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final String USER_SESSION_KEY = "loginedUser";
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "userService")
    private UserService userService;

    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    @PostMapping("")
    public String create(UserDto userDto) {
        userService.add(userDto);
        return "redirect:/users";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        log.debug("LoginUser : {}", loginUser);
        model.addAttribute("user", userService.findById(loginUser, id));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, UserDto target) {
        userService.update(loginUser, id, target);
        return "redirect:"+String.format("/users/%d/form", id);
    }

    @PostMapping("/login")
    public String login(String userId, String password, Model model, HttpSession session) {
        try {
            session.setAttribute(USER_SESSION_KEY, userService.login(userId, password));
        } catch (UnAuthenticationException e) {
            log.debug("login failed");
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "/user/login";
        }
        return "redirect:/";
    }

    @PutMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }
}
