package codesquad.web;

import codesquad.security.HttpSessionUtils;
import codesquad.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Resource(name = "userService")
    private UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public  String login(Model model, HttpSession session, String userId, String password) {
        try {
            session.setAttribute(HttpSessionUtils.USER_SESSION_KEY,userService.login(userId,password));
            return "redirect:/";
        } catch (Exception e) {
            //Todo ajax로 구현해봐도 될거같다. 다음 미션에서 진행해봐야겠다.
            model.addAttribute("errorMessage",e.getMessage());
            return "/user/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }
}
