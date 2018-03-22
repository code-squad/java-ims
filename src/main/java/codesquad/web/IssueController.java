package codesquad.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.HttpSessionUtils;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;

@Controller
@RequestMapping("/issues")
public class IssueController {
	
	@Autowired
	IssueService issueService;
	
	@GetMapping("")
	public String form() {
		return "/issue/form";
	}
	
	@PostMapping("")
	public String create(@LoginUser User loginUser, IssueDto issueDto, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session))
			throw new IllegalStateException("로그인 안된 사용자입니다 삐빅");
		issueService.create(loginUser, issueDto);
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String detail(@PathVariable long id, Model model) {
		model.addAttribute("Issue", issueService.findById(id));
		return "/issue/show";
	}
	
	@PutMapping("/{id}")
	public String update(@LoginUser User loginUser, @PathVariable long id, IssueDto issueDto, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session))
			throw new IllegalStateException("로그인 안된 사용자입니다 삐빅");
		issueService.update(loginUser, id, issueDto);
		return String.format("redirect:/issues/%d", id);
	}
	
	@DeleteMapping("/{id}")
	public String delete(@LoginUser User loginUser, @PathVariable long id, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session))
			throw new IllegalStateException("로그인 안된 사용자입니다 삐빅");
		issueService.delete(loginUser, id);
		return "redirect:/";
	}
	

}
