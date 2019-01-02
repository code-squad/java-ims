package codesquad.web;

import codesquad.domain.label.LabelBody;
import codesquad.service.LabelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping("/labels")
public class LabelController {

    @Resource(name = "labelService")
    private LabelService labelService;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("labels", labelService.findAll());
        return "label/list";
    }

    @GetMapping("/form")
    public String createForm() {
        return "label/form";
    }

    @PostMapping("")
    public String create(@Valid LabelBody labelBody) {
        labelService.create(labelBody);
        return "redirect:/labels";
    }

}
