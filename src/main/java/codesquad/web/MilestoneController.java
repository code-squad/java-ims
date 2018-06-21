package codesquad.web;

import codesquad.domain.User;
import codesquad.security.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static support.domain.Entity.MILESTONE;
import static support.domain.Entity.getEntityName;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {

    @GetMapping("/form")
    public String form(@LoginUser User user, Model model) {
        model.addAttribute(getEntityName(MILESTONE), user);
        return "/milestone/form";
    }
}
