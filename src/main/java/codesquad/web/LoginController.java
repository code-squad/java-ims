package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.domain.User;
import codesquad.security.HttpSessionUtils;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class LoginController {
    private static final Logger log = getLogger(LoginController.class);

    @Resource(name = "userService")
    private UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) throws UnAuthenticationException {
        User loginUser = userService.login(userId, password);
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, loginUser);
        log.debug("session~~~~~ : {}", HttpSessionUtils.isLoginUser(session));
        log.debug("loginUSer TEst : {}", loginUser.toString());
        log.debug(loginUser.toString()+"~~~~~~~");
        log.debug(session.getAttribute(HttpSessionUtils.USER_SESSION_KEY).toString()+"~~~~~~~~~");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }
}

