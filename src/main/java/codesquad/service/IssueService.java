package codesquad.service;

import codesquad.domain.IssueRepository;

import javax.annotation.Resource;

public class IssueService {
    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;


}
