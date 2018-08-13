package codesquad.web;

import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.LabelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/labels")
public class LabelController {

    @Resource(name = "labelService")
    LabelService labelService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("labels", labelService.findAll());
        return "/label/list";
    }

    @GetMapping("/form")
    public String createForm(@LoginUser User user) {
        return "/label/form";
    }

    @PostMapping
    public String create(@LoginUser User user, Label label) {
        labelService.save(label);
        return "redirect:/labels";
    }
}
