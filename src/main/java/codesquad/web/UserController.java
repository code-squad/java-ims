package codesquad.web;

import codesquad.domain.ImageFile;
import codesquad.domain.User;
import codesquad.dto.UserDto;
import codesquad.security.LoginUser;
import codesquad.service.ImageFileService;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Value("${users.img.path}")
    private String path;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "imageFileService")
    private ImageFileService imageFileService;

    @GetMapping("/form")
    public String form() {
        return "/user/join";
    }

    @PostMapping("")
    @Transactional
    public String create(UserDto userDto, MultipartFile pic) throws IOException {
        if (!pic.isEmpty()) {
            ImageFile img = imageFileService.add(pic);
            userService.add(userDto, img.getName());
        } else {
            userService.add(userDto);
        }
        return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        log.debug("LoginUser : {}", loginUser);
        model.addAttribute("user", userService.findById(loginUser, id));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, UserDto target) {
        userService.update(loginUser, id, target);
        return "redirect:/users";
    }

}
