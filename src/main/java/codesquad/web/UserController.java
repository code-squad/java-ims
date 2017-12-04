package codesquad.web;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.model.User;

@Controller
@RequestMapping("user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	private ArrayList<User> users = new ArrayList<User>();

	@RequestMapping("signup")
	public String signUpForm() {
		return "user/join";
	}

	@PostMapping("join")
	public String signUp(String id, String name, String password) {
		users.add(new User(id, name, password));
		log.debug(users.get(0).toString());
		return "redirect:/";
	}

}
