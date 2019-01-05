package codesquad.web;

import codesquad.domain.User;
import codesquad.dto.LabelDto;
import codesquad.security.LoginUser;
import codesquad.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/labels")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("labels", labelService.findAll());
        return "/label/list";
    }

    @GetMapping("/form")
    public String labelForm(@LoginUser User loginUser, Model model) {
        return "/label/form";
    }

    @PostMapping
    public String createLabel(@LoginUser User loginUser, @Valid LabelDto labelDto) {
        labelService.createLabel(loginUser, labelDto);
        return "redirect:/labels";
    }
}
