package codesquad.web;

import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.dto.LabelDto;
import codesquad.security.LoginUser;
import codesquad.service.LabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/labels")
public class LabelController {
    private static final Logger log = LoggerFactory.getLogger(LabelController.class);

    @Resource(name = "labelService")
    LabelService labelService;

    @GetMapping("/form")
    public String form(@LoginUser User loginUser) {
        return "/label/form";
    }

    @PostMapping("")
    public ModelAndView create(@LoginUser User loginUser, LabelDto labelDto, ModelMap model) {
        // TODO 서비스로 넘기는 것 고려
        Label label = new Label(labelDto);
        Label savedLabel = labelService.addLabel(label);
        log.debug("label id : {}", savedLabel.getId());

        model.addAttribute("attribute", savedLabel.getId());

        return new ModelAndView("redirect:/labels", model);
    }

    @GetMapping("")
    public String list(Model model) {
        List<Label> labels = labelService.getLabels();
        model.addAttribute("labels", labels);

        return "/label/list";
    }
}
