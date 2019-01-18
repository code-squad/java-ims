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

    @GetMapping("")
    public String form() {
        return "/label/form";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("labels", labelService.findAll());
        return "/label/list";
    }

    @PostMapping("")
    public String create(@LoginUser User user, Label label, Model model) {
        labelService.create(user, label);
        return "redirect:/labels/list";
    }
}
