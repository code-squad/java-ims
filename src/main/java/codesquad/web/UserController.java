package codesquad.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.UnAuthenticationException;
import codesquad.domain.User;
import codesquad.dto.UserDto;
import codesquad.security.HttpSessionUtils;
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

    @PostMapping("")
    public String create(UserDto userDto) {
        userService.add(userDto);
        return "redirect:/users";
    }
    
    @GetMapping("/loginForm")
    public String loginForm() {
    	return "/user/login";
    }
    
    @PostMapping("/login")
    public String login(UserDto userDto, HttpSession session) {
    	User sessionUser;
		try {
			sessionUser = userService.login(userDto.getUserId(), userDto.getPassword());
		} catch (UnAuthenticationException e) {
			log.info("아이디 또는 비밀번호가 틀립니다");
			e.printStackTrace();
			return "redirect:/users/loginForm";
		}
    	session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, sessionUser);
    	return "redirect:/";
    }
    
    @GetMapping("/logout")
    public String logout(@LoginUser User loginUser, HttpSession session) {
    	session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
    	return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model, HttpSession session) {
        log.debug("LoginUser : {}", loginUser);
    	if(!HttpSessionUtils.isLoginUser(session))
			throw new IllegalStateException("로그인 안된 사용자입니다 삐빅");
        model.addAttribute("user", userService.findById(loginUser, id));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, UserDto target, HttpSession session) {
    	if(!HttpSessionUtils.isLoginUser(session))
			throw new IllegalStateException("로그인 안된 사용자입니다 삐빅");
        userService.update(loginUser, id, target);
        return "redirect:/users";
    }

}
