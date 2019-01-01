package codesquad.web;

import codesquad.domain.milestone.MilestoneBody;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {
    private static final Logger log = getLogger(MilestoneController.class);

    @GetMapping("")
    public String list() {
        return "milestone/list";
    }

    @GetMapping("/form")
    public String form() {
        return "milestone/form";
    }

    @PostMapping("")
    public String create(MilestoneBody milestoneBody) {
        log.debug("milestoneBody : {}", milestoneBody);
        return "redirect:/milestones";
    }
}
