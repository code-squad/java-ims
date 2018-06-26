package codesquad.web;

import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;
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
        Milestone result = milestoneService.create(loginUser, milestoneDto);
        return "redirect:" + result.generateUri();
    }

    @GetMapping
    public String show(Model model) {
        model.addAttribute(getMultipleEntityName(MILESTONE), milestoneService.findAll());
        return "/milestone/list";
    }
}
