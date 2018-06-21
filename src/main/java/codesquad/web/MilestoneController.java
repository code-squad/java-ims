package codesquad.web;

import codesquad.domain.MileStone;
import codesquad.domain.User;
import codesquad.dto.MileStoneDto;
import codesquad.security.LoginUser;
import codesquad.service.MileStoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {
    private static final Logger log =  LoggerFactory.getLogger(MilestoneController.class);

    @Resource(name = "mileStoneService")
    private MileStoneService mileStoneService;

    @GetMapping("")
    public String showList(Model model) {
        List<MileStone> mileStones = mileStoneService.findAll();
        model.addAttribute("milestones", mileStones);
        return "/milestone/list";
    }

    @GetMapping("/form")
    public String form() {
        return "/milestone/form";
    }

    @PostMapping("")
    public String add(MileStoneDto mileStoneDto) {
        log.info("milestone controller called");
        log.info("milestone dto : {}", mileStoneDto.toMileStone().toString());
        mileStoneService.add(mileStoneDto);
        return "redirect:/milestones";
    }
}
