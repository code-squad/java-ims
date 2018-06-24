package codesquad.web;

import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static support.domain.Entity.MILESTONE;
import static support.domain.Entity.getMultipleEntityName;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {
    private static final Logger log = LoggerFactory.getLogger(MilestoneController.class);

    @Autowired
    private MilestoneService milestoneService;

    @GetMapping("/form")
    public String form(@LoginUser User loginUser) {
        return "/milestone/form";
    }

    @PostMapping
    public String create(@LoginUser User loginUser, @Valid MilestoneDto milestoneDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException();
        }
        milestoneService.create(loginUser, milestoneDto);
        return "redirect:/milestones";
    }

    @GetMapping
    public String show(Model model) {
        model.addAttribute(getMultipleEntityName(MILESTONE), milestoneService.findAll());
        return "/milestone/list";
    }
}
