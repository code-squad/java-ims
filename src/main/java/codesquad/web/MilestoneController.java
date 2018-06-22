package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.dto.MilestoneDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {
    private static final Logger logger = LoggerFactory.getLogger(MilestoneController.class);

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private IssueService issueService;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("milestones", milestoneService.findAll());
        return "/milestone/list";
    }

    @GetMapping("/form")
    public String form(@LoginUser User loginUser) {
        return "/milestone/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, MilestoneDto milestoneDto) {
        logger.debug("MilestoneDTO: {}", milestoneDto);
        milestoneService.create(milestoneDto);
        return "redirect:/milestones";
    }

    @PostMapping("/{milestoneId}")
    public String add(@LoginUser User loginUser, IssueDto issueDto, @PathVariable long milestoneId) {
        Issue issue = issueService.findById(issueDto.getId());
        milestoneService.add(milestoneId, issue);
        return "redirect:/issues/" + issueDto.getId();
    }
}
