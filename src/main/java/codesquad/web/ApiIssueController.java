package codesquad.web;

import codesquad.domain.IssueMenus;
import codesquad.domain.Label;
import codesquad.domain.MenuEntity;
import codesquad.service.IssueService;
import codesquad.service.LabelService;
import codesquad.service.MilestoneService;
import codesquad.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/issue/{id}")
public class ApiIssueController {
    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @Resource(name = "labelService")
    private LabelService labelService;

    @Resource(name = "userService")
    private UserService userService;


    @GetMapping("/milestones")
    public IssueMenus findMilestones(@PathVariable long id) {
        return new IssueMenus(id,issueService.findById(id).get().getMilestone() , milestoneService.findAll());
    }

    @GetMapping(value = "/labels")
    public IssueMenus findLabels(@PathVariable long id) {
        return new IssueMenus(id,issueService.findById(id).get().getLabel(), labelService.findAll());
    }

    @GetMapping("/assignees")
    public IssueMenus findAssignees(@PathVariable long id) {
        return new IssueMenus(id,issueService.findById(id).get().getAssignee(), userService.findAll());
    }

}
