package codesquad.web;

import codesquad.service.LabelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/labels")
public class LabelController {
    @Resource(name = "labelService")
    private LabelService labelService;

    @GetMapping("")
    public String createForm() {
        return "label/form";
    }
}
