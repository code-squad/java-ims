package codesquad.service;

import codesquad.domain.IssueRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IssueService {
    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;


}
