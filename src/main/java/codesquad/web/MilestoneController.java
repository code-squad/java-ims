package codesquad.web;

import codesquad.domain.User;
import codesquad.security.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("milestone")
public class MilestoneController {

    @GetMapping("/list")
    public String showList() {
        return "/milestone/list";
    }

    @GetMapping("/form")
    public String form() {
        return "/milestone/form";
    }

//    @PostMapping("")
//    public String add(@LoginUser User loginUser, Model model) {
//
//    }
}
