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

import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.dto.LabelDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.LabelService;


@Controller
@RequestMapping("/labels")
public class LabelController {
	private static final Logger log = LoggerFactory.getLogger(LabelController.class);

	@Resource(name = "labelService")
	private LabelService labelService;
	
	@Resource(name = "issueService")
	private IssueService issueService;
	
	@GetMapping("")
	public String show_list(@LoginUser User loginUser, Model model) {
		model.addAttribute("labels", labelService.findAll());
		log.debug("labels : {}", labelService.findAll() );
		return "label/list";
	}
	
	@GetMapping("/form")
	public String form(@LoginUser User loginUser) {
		return "/label/form";
	}

	// view로 부터 dto 형태로 데이터를 받아 처리.
	@PostMapping("")
	public String create(LabelDto labelDto, @LoginUser User loginUser) {
		log.debug(labelDto.toString());
		labelService.add(labelDto);
		return "redirect:/labels";
	}
	
	@GetMapping("/{id}")
	public String showDetail(@LoginUser User loginUser, @PathVariable long id, Model model) {
		model.addAttribute("label", labelService.findById(id));
		return "/label/show";
	}
	
	@GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
    		model.addAttribute("label", labelService.findById(id));
    		return "/label/updateForm";
    }
    
    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, String subject, Model model) {
		Label label = labelService.update(loginUser, id, subject);
    		model.addAttribute("label", label);
    		return "/label/show";
    }
    
    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) {
    		labelService.delete(loginUser, id);
    		return "redirect:/";
    }
    

}
