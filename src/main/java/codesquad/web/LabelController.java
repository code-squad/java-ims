package codesquad.web;

import codesquad.domain.ContentsBody;
import codesquad.domain.Label;
import codesquad.service.LabelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/label")
public class LabelController {
    @Resource(name = "labelService")
    private LabelService labelService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("labels", labelService.findAll());
        return "/label/list";
    }

    @GetMapping("/form")
    public String createForm() {
        return "/label/form";
    }

    @PostMapping("")
    public String create(ContentsBody contentsBody) {
        labelService.add(Label.ofBody(contentsBody));
        return "redirect:/label/list";
    }

}
