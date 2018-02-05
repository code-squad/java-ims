package codesquad.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import codesquad.UnAuthenticationException;
import codesquad.security.HttpSessionUtils;
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
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "userService")
    private UserService userService;

    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }

    @GetMapping("")
    public String show(@LoginUser User loginUser) {
        long id = loginUser.getId();
        return "redirect:/users/" + id + "/form";
    }

    @PostMapping("")
    public String create(UserDto userDto) {
        userService.add(userDto);
        return "redirect:/users";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        if (loginUser.getId() != id) return "redirect:/users/{id}/form";
        log.debug("LoginUser : {}", loginUser);
        model.addAttribute("user", userService.findById(loginUser, id));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, UserDto target) {
        userService.update(loginUser, id, target);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @GetMapping("/login_failed")
    public String loginFailForm() {
        return "user/login_failed";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session){
        User user;
        try{
            user = userService.login(userId, password);
        } catch (UnAuthenticationException exception){
            return "redirect:/users/login_failed";
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
