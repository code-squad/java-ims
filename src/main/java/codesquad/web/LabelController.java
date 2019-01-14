package codesquad.web;

import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.LabelService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/labels")
public class LabelController {
    private static final Logger log = getLogger(LabelController.class);

    @Resource(name = "labelService")
    private LabelService labelService;

    @GetMapping("/list")
    public String createListForm(@LoginUser User loginUser, Model model) {
        model.addAttribute("label", labelService.findAll());
        return "/label/list";
    }

    @GetMapping("")
    public String createForm(@LoginUser User loginUser) {
        return "/label/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, Label label) {
        labelService.create(loginUser, label);
        return "redirect:/labels/list";
    }
}
