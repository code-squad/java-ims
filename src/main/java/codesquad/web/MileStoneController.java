package codesquad.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.MileStoneDto;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.MileStoneService;

@Controller
@RequestMapping("/mileStones")
public class MileStoneController {

	@Resource(name = "mileStoneService")
	private MileStoneService mileStoneService;

	@GetMapping("")
	public String show_list(@LoginUser User loginUser, Model model) {
		model.addAttribute("mileStones", mileStoneService.findAll());
		return "mileStone/list";
	}

	@GetMapping("/form")
	public String createForm(@LoginUser User loginUser) {
		return "mileStone/form";
	}

	@PostMapping("")
	public String create(@LoginUser User loginUser, MileStoneDto mileStoneDto) {
		mileStoneService.add(mileStoneDto);
		return "redirect:/mileStones";
	}
}
