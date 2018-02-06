package codesquad.web;

import codesquad.UnAuthorizedException;
import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {
	private static final Logger log = LoggerFactory.getLogger(MilestoneController.class);

	@Resource(name = "milestoneService")
	private MilestoneService milestoneService;

	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("milestones", milestoneService.findAll());
		return "/milestone/list";
	}

	@GetMapping("/form")
	public String form(@LoginUser User loginUser) {
		if (loginUser == null) {
			throw new UnAuthorizedException();
		}
		return "/milestone/form";
	}

	@PostMapping("")
	public String create(@LoginUser User loginUser, MilestoneDto milestoneDto) throws IllegalArgumentException {
		if (loginUser == null) {
			throw new UnAuthorizedException();
		}

		log.debug("milestoneDto: {}", milestoneDto);

		milestoneService.add(milestoneDto);
		return "redirect:/milestones";
	}
}
