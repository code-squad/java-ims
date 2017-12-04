package codesquad.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.model.User;
import codesquad.model.UserRepository;

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
		userRepository.save(user);
		log.debug(user.toString());
		return "redirect:/";
	}

}
