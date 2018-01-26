package codesquad.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.User;
import codesquad.dto.LabelDto;
import codesquad.security.LoginUser;
import codesquad.service.LabelService;

@Controller
@RequestMapping("/labels")
public class LabelController {
	private static final Logger log = LoggerFactory.getLogger(LabelController.class);
	
	@Resource(name = "labelService")
	private LabelService labelService;
	
	@GetMapping("")
	public String labelView(Model model) {
		model.addAttribute("labels", labelService.findAll());
		return "/label/list";
	}
	
	@GetMapping("/form")
	public String addLabelForm() {
		return "/label/form";
	}
	
	@PostMapping("")
	public String addLabel(@LoginUser User user, LabelDto labelDto, BindingResult errors, Model model) {
		if(errors.hasErrors()) {
			return "/label/form";
		}
		labelService.saveLabel(labelDto);
		model.addAttribute("labels", labelService.findAll());
		return "/label/list";
	}
	
	@GetMapping("/{id}")
	public String updateLabelView(@PathVariable Long id, Model model) {
		model.addAttribute("label", labelService.findById(id)._toLabelDto());
		return "/label/updateForm";
	}
	
	@PutMapping("/{id}")
	public String updateLabel(@LoginUser User user, @PathVariable Long id, LabelDto labelDto, BindingResult errors, Model model) {
		if(errors.hasErrors()) {
			return "/label/form";
		}
		labelService.updateLabel(labelDto, id);
		model.addAttribute("labels", labelService.findAll());
		return "/label/list";
	}
	
	@DeleteMapping("/{id}")
	public String deleteLabel(@LoginUser User loginUser, @PathVariable Long id, Model model) {
		labelService.delete(id);
		model.addAttribute("labels", labelService.findAll());
		return "/label/list";
	}
}