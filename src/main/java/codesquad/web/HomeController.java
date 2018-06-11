package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IssueRepository issueRepository;

    @GetMapping("/")
    public String home(Model model) {
        logger.debug("Retrieving list of all issues...");
        List<Issue> issues = issueRepository.findAllByDeletedFalse();
        logger.debug("Number of Issues in Repo: {}", issues.size());
        model.addAttribute("issues", issues);
        return "index";
    }
}
