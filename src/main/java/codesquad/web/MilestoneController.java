package codesquad.web;

import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/milestone")
public class MilestoneController {

    @Autowired
    private MilestoneService milestoneService;

    @Value("${error.not.supported}")
    private String formatError;

    private static final Logger logger = getLogger(MilestoneController.class);

    @GetMapping
    public String milestoneList(Model model) {
        logger.debug("Call milestone Method call");
        model.addAttribute("milestones", milestoneService.findAllMilestone());
        return "/milestone/list";
    }

    @GetMapping("/form")
    public String milestoneForm(@LoginUser User loginUser) {
        logger.debug("Call milestoneFrom Method call");
        return "/milestone/form";
    }

    @PostMapping
    public String milestoneCreate(@LoginUser User loginUser, @Valid MilestoneDto milestoneDto, BindingResult bindingResult) {
        logger.debug("Call milestoneCreate Method call, MileStoneDto : {}", milestoneDto.toString());
        if(bindingResult.hasErrors()) {
            logger.debug(formatError);
            throw new EntityNotFoundException(formatError);
        }
        Milestone milestone = milestoneService.saveMilestone(loginUser, milestoneDto);
        return "redirect:/milestone";
    }
}
