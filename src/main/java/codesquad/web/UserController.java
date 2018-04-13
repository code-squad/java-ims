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
	public String login(String userId, String password, HttpSession session, Model model) {
		try {
			User loginUser = userService.login(userId, password);
			session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, loginUser);
		} catch (UnAuthenticationException e) {
			log.debug("ERROR!! : 아이디 또는 비밀번호가 다릅니다.");
			e.printStackTrace();
			return "redirect:/users/loginFail";
		}
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		log.debug("======== Success to LOGOUT!! ========");

		return "redirect:/";
	}

	@GetMapping("/{id}/updateForm")
	public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
		log.debug("LoginUser : {}", loginUser);
		model.addAttribute("user", userService.findById(loginUser, id));
		return "/user/updateForm";
	}
	
	@GetMapping("/loginFail")
	public String fail(Model model) {
		model.addAttribute("errorMessage", "로그인 실패. 아이디와 비밀번호를 확인해주세요.");
		return "/user/login";
	}

	@PutMapping("/{id}")
	public String update(@LoginUser User loginUser, @PathVariable long id, String name, UserDto target) {
		UserDto loginUserDto = new UserDto(loginUser.getUserId(), loginUser.getPassword(), name);
		userService.update(loginUser, id, loginUserDto);
		return "redirect:/";
	}

}
