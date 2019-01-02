package codesquad.web;

import codesquad.domain.milestone.Milestone;
import codesquad.domain.milestone.MilestoneBody;
import codesquad.service.MilestoneService;
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

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @PostMapping("")
    public ResponseEntity<Void> create(@Valid @RequestBody MilestoneBody milestoneBody) {
        Milestone savedMilestone = milestoneService.create(milestoneBody);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/milestones/" + savedMilestone.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Milestone show(@PathVariable long id) {
        return milestoneService.findById(id);
    }
}
