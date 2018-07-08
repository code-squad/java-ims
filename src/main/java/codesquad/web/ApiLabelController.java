package codesquad.web;

import codesquad.domain.Label;
import codesquad.dto.LabelsDto;
import codesquad.service.LabelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/labels")
public class ApiLabelController {
    @Resource(name = "labelService")
    LabelService labelService;

    @GetMapping("")
    public LabelsDto list() {
        LabelsDto labelsDto = new LabelsDto();

        List<Label> labels = labelService.getLabels();
        for (Label label : labels) {
            labelsDto.addLabel(label.getId(), label.getSubject());
        }

        return labelsDto;
    }
}