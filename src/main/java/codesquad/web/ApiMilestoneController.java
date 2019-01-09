package codesquad.web;

import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/milestones")
public class ApiMilestoneController {
    private static final Logger log = getLogger(ApiMilestoneController.class);

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @PostMapping("")
    public ResponseEntity<Milestone> create(@LoginUser User user, @Valid @RequestBody Milestone milestone) {

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/milestones/list"));
        return new ResponseEntity<Milestone>(milestoneService.create(user, milestone),headers, HttpStatus.CREATED);
    }
}
