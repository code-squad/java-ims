package codesquad.web;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {
	private static final Logger log = LoggerFactory.getLogger(MilestoneController.class);
	
	@Resource(name = "milestoneService")
	private MilestoneService milestoneService;
	
	@GetMapping("")
	public String milestoneList(Model model) {
		model.addAttribute("milestone", milestoneService.findAll());
		return "/milestone/list";
	}
	
	@GetMapping("/form")
	public String milestoneForm() {
		return "/milestone/form";
	}
	
	@PostMapping("")
	public String saveMilestoneForm(@LoginUser User loginUser, @Valid MilestoneDto milestoneDto, BindingResult errors, Model model) {
		if(errors.hasErrors()) {
			return "/issue/form";
		}
		milestoneDto.addWriter(loginUser);
		log.debug("milestonetest : {}", milestoneDto);
		model.addAttribute("milestone", milestoneService.add(milestoneDto));
		return "/milestone/list";
	}
}
