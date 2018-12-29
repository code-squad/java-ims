package codesquad.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/issue")
public class IssueController {
    private static final Logger log = LogManager.getLogger(IssueController.class);


}
