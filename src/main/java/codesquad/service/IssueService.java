package codesquad.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IssueService {
    @Resource(name = "issueService")
    private IssueService issueService;

}
