package codesquad.web;

import codesquad.domain.User;
import codesquad.security.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private static final Logger log =  LoggerFactory.getLogger(HomeController.class);
    private static final String USER_SESSION_KEY = "loginedUser";

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        User loginedUser = (User) session.getAttribute(USER_SESSION_KEY);
        log.debug("LoginedUser : {}", loginedUser);
        if (loginedUser == null) {
            model.addAttribute("isLogined", false);
            return "index";
        }
        model.addAttribute("isLogined", true);
        return "index";
    }
}
