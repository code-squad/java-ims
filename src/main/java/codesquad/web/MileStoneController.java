package codesquad.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.service.MileStoneService;

@Controller
@RequestMapping("/milestone")
public class MileStoneController {
	
	@Resource(name="mileStoneService")
	private MileStoneService mileStoneService;
	
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("mileStones", mileStoneService.findAll());
		return "/milestone/list";
	}

}
