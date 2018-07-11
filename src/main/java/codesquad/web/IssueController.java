package codesquad.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/issues")
public class IssueController {

    @GetMapping("/form")
    public String createForm() {
        return "/issue/form.html";
    }
}
