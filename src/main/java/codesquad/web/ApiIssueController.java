package codesquad.web;

import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {
    private static final Logger log = getLogger(ApiIssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;
}
