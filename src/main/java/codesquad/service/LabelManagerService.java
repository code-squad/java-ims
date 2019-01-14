//package codesquad.service;
//
//import codesquad.domain.Issue;
//import codesquad.domain.Label;
//import codesquad.domain.LabelManager;
//import codesquad.repository.LabelManagerRepository;
//import org.slf4j.Logger;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
//import static org.slf4j.LoggerFactory.getLogger;
//
//@Service("labelManagerService")
//public class LabelManagerService {
//    private static final Logger log = getLogger(LabelManagerService.class);
//
//    @Resource(name = "labelManagerRepository")
//    private LabelManagerRepository labelManagerRepository;
//
//    public LabelManager create(Issue issue, Label label) {
//        return labelManagerRepository.save(new LabelManager(issue,label));
//    }
//}
