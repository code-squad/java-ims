package codesquad.web;

import codesquad.domain.User;
import codesquad.security.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/milestones")
public class milestoneController {

    @GetMapping("")
    public String list() {
        return "milestone/list";
    }

    @GetMapping("/form")
    public String milstoneForm(@LoginUser User loginUser) {
        return "milestone/form";
    }
}

