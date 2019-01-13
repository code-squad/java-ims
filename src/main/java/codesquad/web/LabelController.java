package codesquad.web;

import codesquad.domain.label.Label;
import codesquad.domain.user.User;
import codesquad.security.LoginUser;
import codesquad.service.LabelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/labels")
public class LabelController {
    @Resource(name = "labelService")
    private LabelService labelService;

    @GetMapping("")
    public String createForm(@LoginUser User loginUser) {
        return "label/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, String label) {
        labelService.add(new Label(label), loginUser);
        return "redirect:/";
    }
}
