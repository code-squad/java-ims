package codesquad.web;

import codesquad.dto.MilestoneDto;
import codesquad.service.MilestoneService;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {

    private static final Logger log = LoggerFactory.getLogger(MilestoneController.class);

    @Autowired
    private MilestoneService milestoneService;

    @GetMapping("/form")
    public String createForm() {
        return "milestone/form";
    }

    @PostMapping("")
    public String createMilestone(MilestoneDto milestoneDto) {
        milestoneService.create(milestoneDto);
        return "redirect:/";
    }
}
