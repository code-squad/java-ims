package codesquad.web;

import codesquad.domain.Milestone;
import codesquad.dto.MilestonesDto;
import codesquad.service.MilestoneService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/milestones")
public class ApiMilestoneController {
    @Resource(name = "milestoneService")
    MilestoneService milestoneService;

    @GetMapping("")
    public MilestonesDto list() {
        MilestonesDto milestonesDto = new MilestonesDto();

        List<Milestone> milestones = milestoneService.getMilestones();
        for (Milestone milestone: milestones) {
            milestonesDto.addMilestones(milestone.getId(), milestone.getSubject());
        }

        return milestonesDto;
    }
}
