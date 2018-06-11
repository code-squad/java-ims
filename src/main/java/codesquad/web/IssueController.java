package codesquad.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private static final Logger logger = LoggerFactory.getLogger(IssueController.class);

    @GetMapping("/form")
    public String form() {
        logger.debug("Getting issue form...");
        return "/issue/form.html";
    }


}
