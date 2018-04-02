package codesquad.web;

import java.time.LocalDateTime;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import codesquad.service.MilestoneService;

@Controller
@RequestMapping("/milestones")
public class MileStoneController {
	private Logger log = LoggerFactory.getLogger(MileStoneController.class);

	@Resource(name = "milestoneService")
	MilestoneService milestoneService;

	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("milestones", milestoneService.list());
		return "/milestone/list";
	}

	@GetMapping("/form")
	public String form() {
		return "/milestone/form";
	}

	@PostMapping("")
	public String create(MilestoneDto milestoneDto, HttpSession session) {
		log.debug("milestoneDto : %", milestoneDto);
		log.debug("upupup");

		User writer = (User) session.getAttribute("loginedUser");
		if (writer == null) {
			return "redirect:/users/login";
		}
		milestoneService.create(milestoneDto);
		return "redirect:/milestones/list";
	}
}
