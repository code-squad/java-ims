package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.service.IssueService;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;

@RestController
@RequestMapping("/api/labels")
public class LabelController {

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("")
    public ImmutableMap<Label, Collection<Issue>> listPage() {
        ImmutableListMultimap<Label, Issue> labelOrderedIssues = issueService.findByLabel();
        return labelOrderedIssues.asMap();
    }

}
