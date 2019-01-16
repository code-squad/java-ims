package codesquad.web;

import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/milestone")
public class MilestoneController {

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = getLogger(MilestoneController.class);

    @GetMapping
    public String milestoneList(Model model) {
        model.addAttribute("milestones", milestoneService.findAllMilestone());
        return "/milestone/list";
    }

    @GetMapping("/form")
    public String milestoneForm(@LoginUser User loginUser) {
        return "/milestone/form";
    }

    @PostMapping
    public String milestoneCreate(@LoginUser User loginUser, @Valid MilestoneDto milestoneDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new EntityNotFoundException(
                    messageSource.getMessage("error.not.supported", null, Locale.getDefault()));
        }
        milestoneService.saveMilestone(loginUser, milestoneDto);
        return "redirect:/milestone";
    }
}
