package codesquad.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import codesquad.UnAuthenticationException;
import codesquad.exception.AlreadyLoginException;
import codesquad.security.HttpSessionUtils;
import codesquad.util.Result;
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

import static codesquad.security.HttpSessionUtils.USER_SESSION_KEY;
import static codesquad.security.HttpSessionUtils.isLoginUser;
import static codesquad.util.Result.LOGIN_NOT_MATCH_WARNING;

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

    @GetMapping("/login/form")
    public String loginForm(HttpSession session) {
        if (isLoginUser(session)) {
            throw new AlreadyLoginException(); // 이미 로그인 한 사용자의 접근 방지
        }
        return "/user/loginForm";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session, Model model) {
        if (isLoginUser(session)) {
            throw new AlreadyLoginException(); // 이미 로그인 한 사용자의 접근 방지
        }
        log.debug("login - id : {}, pw : {}", userId, password);
        try {
            User loginUser = userService.login(userId, password);
            session.setAttribute(USER_SESSION_KEY, loginUser);
            return "redirect:/";
        } catch (UnAuthenticationException e) {
            model.addAttribute("errorMessage", Result.fail(LOGIN_NOT_MATCH_WARNING).getErrorMessage());
            return "user/loginForm";
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
        userService.update(loginUser, id, target);
        return "redirect:/users";
    }

}
