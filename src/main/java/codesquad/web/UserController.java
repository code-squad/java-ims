package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.domain.User;
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

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "userService")
    private UserService userService;

    @GetMapping("/login")
    public String loginForm(){
        return "/user/login";
    }

    @PostMapping("/loginUser")
    public String login(UserDto userDto, HttpSession session) throws UnAuthenticationException {
        userService.login(userDto._toUser().getUserId(), userDto._toUser().getPassword());
        session.setAttribute("loginedUser", userService.findByUserId(userDto._toUser().getUserId()));
        return "/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginedUser");
        return "redirect:/";
    }

    @GetMapping("/form")
    public String join() {
        return "/user/join";
    }

    @PostMapping("")
    public String create(UserDto userDto) {
        userService.add(userDto);
        return "redirect:/users";
    }

    @GetMapping("/{id}/modify")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        model.addAttribute("user", userService.findByUserId(loginUser.getUserId()));
        return "/user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String update(@LoginUser User loginUser, @PathVariable long id, UserDto target) {
        userService.update(loginUser, id, target);
        return "/index";
    }
}