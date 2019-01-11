package codesquad.service;

import codesquad.domain.issue.CommentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommentService {
    @Resource(name = "commentRepository")
    private CommentRepository commentRepository;

}
