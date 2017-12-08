package codesquad.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.exception.InvalidUserException;
import codesquad.model.Milestone;
import codesquad.model.MilestoneRepository;
import codesquad.model.User;
import codesquad.util.HttpSessionUtil;

@Controller
@RequestMapping("milestone")
public class MilestoneController {
	@Autowired
	private MilestoneRepository milestoneRepository;
	@GetMapping
	public String createForm() {
		return "milestone/form";
	}

	@PostMapping
	public String create(Milestone milestone, HttpSession session) {
		User sessionUser = HttpSessionUtil.loginSessionUser(session);
		if(sessionUser == null) {
			throw new InvalidUserException("로그인 정보가 없습니다.");
		}
		milestone.setWriter(sessionUser);
		milestoneRepository.save(milestone);
		return "redirect:/milestone/list";
	}

	@GetMapping("list")
	public String list(Model model) {
		model.addAttribute("milestone", milestoneRepository.findAll());
		return "milestone/list";
	}
}