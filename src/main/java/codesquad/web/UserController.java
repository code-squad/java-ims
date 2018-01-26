package codesquad.web;

import java.util.Optional;

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

	@GetMapping("/{id}/form")
	public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
		log.debug("LoginUser : {}", loginUser);
		model.addAttribute("user", userService.findById(loginUser, id)._toUserDto());
		return "/user/updateForm";
	}

	@PutMapping("/{id}")
	public String update(@LoginUser User loginUser, @PathVariable long id, UserDto target) {
		log.debug(target.toString());
		userService.update(loginUser, id, target);
		return "redirect:/";
	}

	@GetMapping("/login")
	public String loginView() {
		return "/user/login";
	}

	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		Optional<User> user = userService.getUserRepositoryFindUserId(userId);
		if (!user.isPresent() || !user.get().matchPassword(password)) {
			return loginFail();
		}
		System.out.println("Login Success!");
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user.get());
		return "redirect:/";
	}

	private String loginFail() {
		System.out.println("Login Failure!");
		return "/user/login_failed";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}
}
