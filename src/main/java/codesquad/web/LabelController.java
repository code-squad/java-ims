package codesquad.web;

import codesquad.domain.LabelRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/labels")
public class LabelController {
    @Resource(name = "labelRepository")
    private LabelRepository labelRepository;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("labels", labelRepository.findAll());
        return "/label_list";
    }
}
