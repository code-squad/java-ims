package codesquad.domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/labels")
public class LabelController {

    @GetMapping("")
    public String list() {
        return "label/list";
    }

    @GetMapping("/form")
    public String createForm() {
        return "label/form";
    }


}
