package codesquad.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.UnAuthenticationException;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.UserDto;
import codesquad.security.HttpSessionUtils;
import codesquad.security.LoginUser;
import codesquad.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepository;

	@Resource(name = "userService")
	private UserService userService;
	
	@GetMapping("/join")
	public String join() {
		return "/user/join";
	}

	@PostMapping("/create")
	public String create(String userId, String password, String name) {
		UserDto userDto = new UserDto(userId, password, name);
		userService.add(userDto);
		return "redirect:/";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) throws UnAuthenticationException {
		User loginUser = userService.login(userId, password);
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, loginUser);
		return "redirect:/";
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
