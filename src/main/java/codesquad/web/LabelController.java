package codesquad.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.UnAuthenticationException;
import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.LabelService;

@Controller
@RequestMapping("/label")
public class LabelController {
	private static final Logger log = LoggerFactory.getLogger(MilestoneController.class);
	
	@Resource (name = "labelService")
	private LabelService labelService;
	
	@GetMapping("")
	public String list(Model model) {
		log.debug("label controller(list) in.");
		model.addAttribute("labels", labelService.findNotDeleted());
		return "/label/list";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable long id, Model model) {
		Label label = labelService.findOne(id);
		model.addAttribute("label", label);
		return "/label/show";
	}
	
	@GetMapping("/form")
	public String form(@LoginUser User loginUser) {
		return "/label/form";
	}
	
	@PostMapping("/newLabel")
	public String create(@LoginUser User loginUser, String subject) {
		labelService.create(loginUser, subject);
		return "redirect:/label";
	}
	
	@GetMapping("/{id}/updateForm")
	public String updateForm(@PathVariable long id, Model model) {
		Label label = labelService.findOne(id);
		model.addAttribute("label", label);
		return "/label/labelUpdateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@LoginUser User loginUser, @PathVariable long id, String subject) {
		try {
			labelService.update(loginUser, id, subject);
		} catch (UnAuthenticationException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
		}
		return "redirect:/label/{id}";
	}
	
	@DeleteMapping("/{id}")
	public String delete(@LoginUser User loginUser, @PathVariable long id) {
		log.debug("INININ");
		try {
			labelService.delete(loginUser, id);
		} catch (UnAuthenticationException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
		}
		return "redirect:/label/";
	}
}
