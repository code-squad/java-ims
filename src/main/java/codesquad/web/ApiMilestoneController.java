package codesquad.web;

import codesquad.domain.Milestone;
import codesquad.dto.MilestoneDto;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/milestones")
public class ApiMilestoneController {

    private static final Logger log = LoggerFactory.getLogger(ApiMilestoneController.class);

    @Autowired
    private MilestoneService milestoneService;

    @GetMapping("")
    public List<Milestone> showAllMilestones() {
        return milestoneService.findAllMileStone();
    }

    @PostMapping("")
    public ResponseEntity<Void> create(MilestoneDto milestoneDto) {
        Milestone milestone = milestoneService.create(milestoneDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(milestone.generateUrl()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
