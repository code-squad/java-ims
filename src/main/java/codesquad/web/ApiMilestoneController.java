package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/milestones")
public class ApiMilestoneController {
    
    private static final Logger log = LoggerFactory.getLogger(ApiMilestoneController.class);

    @Resource
    private MilestoneService milestoneService;

    @PostMapping("")
    public ResponseEntity<Void> createMilestone(@LoginUser User loginUser, @Valid@RequestBody MilestoneDto milestoneDto) {
        Milestone milestone = milestoneService.create(milestoneDto);
        log.debug("Milestone : {}", milestone);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/milestones/" + milestone.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
