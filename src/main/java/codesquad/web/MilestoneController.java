package codesquad.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/milestone")
public class MilestoneController {

    @GetMapping("/list")
    public String list() {
        return "/milestone/list";
    }

    @GetMapping("/form")
    public String create() {
        return "/milestone/form";
    }
}
