package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.service.IssueService;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Collection;

@Controller
@RequestMapping("/labels")
public class LabelController {

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("") // 라벨끼리 묶는 것은 했는데..
    public @ResponseBody ImmutableMap<Label, Collection<Issue>> listPage(Model model) {
        ImmutableListMultimap<Label, Issue> labelOrderedIssues = issueService.findByLabel();
        return labelOrderedIssues.asMap();
    }

}
