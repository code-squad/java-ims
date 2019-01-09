package codesquad.web;

import codesquad.domain.label.Label;
import codesquad.service.LabelService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issue/{id}/label")
public class ApiLabelController {
    private static final Logger log = getLogger(ApiLabelController.class);

    @Autowired
    private LabelService labelService;

    @GetMapping("")
    public List<Label> show() {
        log.debug("label show");
        return labelService.findAll();
    }
}
