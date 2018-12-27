package codesquad.web;

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

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "userService")
    private UserService userService;

    @GetMapping("/form")
    public String form() {
        log.debug("Call form() Method!");
        return "user/join";
    }

    @GetMapping("/login")
    public String login() {
        log.debug("Call login() Method!");
        return "user/login";
    }

    @PostMapping("")
    public String create(UserDto userDto) {
        userService.add(userDto);
        return "redirect:/user";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        log.debug("LoginUser : {}", loginUser);
        model.addAttribute("user", userService.findById(loginUser, id));
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, UserDto target) {
        userService.update(loginUser, id, target);
        return "redirect:/user";
    }

}
