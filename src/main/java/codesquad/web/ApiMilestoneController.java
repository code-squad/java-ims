package codesquad.web;

import codesquad.domain.milestone.Milestone;
import codesquad.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/issue/{id}/milestone")
public class ApiMilestoneController {

    @Autowired
    MilestoneService milestoneService;

    @GetMapping("")
    public List<Milestone> list() {
        return milestoneService.findAll();
    }
}
