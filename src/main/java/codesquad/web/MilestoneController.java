package codesquad.web;

import codesquad.domain.User;
import codesquad.security.HttpSessionUtils;
import codesquad.security.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("milestones")
public class MilestoneController {

    @GetMapping()
    public String list() {
        return "/milestone/list";
    }

    @GetMapping("/form")
    public String createForm(@LoginUser User user) {
        return "/milestone/form";
    }
}
