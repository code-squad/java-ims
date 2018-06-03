package codesquad.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.User;
import codesquad.dto.MileStoneDto;
import codesquad.security.HttpSessionUtils;
import codesquad.security.LoginUser;
import codesquad.service.MileStoneService;

@Controller
@RequestMapping("/milestone")
public class MileStoneController {

	@Resource(name = "mileStoneService")
	private MileStoneService mileStoneService;

	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("mileStones", mileStoneService.findAll());
		return "/milestone/list";
	}

	@GetMapping("/form")
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/user/login";
		}
		return "/milestone/form";
	}
	
	@PostMapping("")
	public String create(@LoginUser User loginUser, @Valid MileStoneDto mileStoneDto, HttpSession session) {
		
		if(loginUser.isGuestUser()) {
			return "/user/login";
		}
		mileStoneService.add(loginUser,mileStoneDto);
		return "redirect:/milestone";
	}

}
