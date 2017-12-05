package codesquad.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.model.User;
import codesquad.model.UserRepository;
import codesquad.util.HttpSessionUtil;

@Controller
@RequestMapping("user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("signup")
	public String signUpForm() {
		return "user/join";
	}

	@PostMapping("signup")
	public String signUp(User user) {
		if (userRepository.findOne(user.getId()) != null) {
			log.debug("이미 존재하는 아이디입니다.");
			return "redirect:signup";
		}
		userRepository.save(user);
		log.debug(user.toString());
		return "redirect:/";
	}

	@RequestMapping("login")
	public String loginForm() {
		return "user/login";
	}

	@PostMapping("login")
	public String login(String id, String password, HttpSession session) {
		if (HttpSessionUtil.isLoginSession(session)) {
			log.debug("이미 로그인 상태 입니다.");
			return "redirect:/";
		}
		User user = userRepository.findOne(id);
		if (user == null) {
			log.debug(id + " 아이디가 잘 못 되었습니다.");
			return "redirect:/user/login";
		}
		if (user.matchingPassword(password)) {
			log.debug(id + " 비밀번호가 잘 못 되었습니다.");
			return "redirect:/user/login";
		}
		session.setAttribute(HttpSessionUtil.SESSION_USER_NAME, user);
		log.debug(id + " 로그인에 성공하였습니다.");
		log.debug(session.getAttribute(HttpSessionUtil.SESSION_USER_NAME).toString());
		return "redirect:/";
	}

	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtil.SESSION_USER_NAME);
		return "redirect:/";
	}

	@RequestMapping("edit")
	public String editForm(HttpSession session, Model model) {
		model.addAttribute("user", HttpSessionUtil.loginSessionUser(session));
		return "/user/edit";
	}

	@PostMapping("edit")
	public String edit(HttpSession session, User updateUser) {
		User user = HttpSessionUtil.loginSessionUser(session);
		user.update(updateUser);
		userRepository.save(user);
		return "redirect:/";
	}
}
