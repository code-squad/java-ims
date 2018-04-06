package codesquad.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.LabelService;

@Controller
@RequestMapping("/labels")
public class LabelController {
	
	@Autowired
	LabelService labelService;
	
	@Autowired
	IssueService issueService;
	
	@PostMapping("")
	public String create(@LoginUser User loginUser, Label label) {
		labelService.createLabel(label);
		return "redirect:/labels";
	}
	
	@GetMapping("")
	public String list(@LoginUser User loginUser, Model model) {
		model.addAttribute("Labels", labelService.findLabelAll());
		return "/label/list";
	}

	@GetMapping("/form")
	public String form(@LoginUser User loginUser){
		return "/label/form";
	}
	
	@PutMapping("/{id}")
	public String update(@LoginUser User loginUser,@PathVariable long id, Label label) {
		labelService.update(id, label);
		return "redirect:/labels";
	}
	
	@DeleteMapping("/{id}")
	public String delete(@LoginUser User loginUser, @PathVariable long id) {
		labelService.delete(id);
		return "redirect:/labels";
	}
}
