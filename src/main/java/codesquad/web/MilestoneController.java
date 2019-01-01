package codesquad.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {

    @GetMapping("")
    public String list() {
        return "milestone/list";
    }

    @GetMapping("/form")
    public String form() {
        return "milestone/form";
    }
}
