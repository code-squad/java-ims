package codesquad.web;

import codesquad.UnsupportedFormatException;
import codesquad.domain.Answer;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AnswerService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import support.domain.ErrorMessage;

import javax.validation.Valid;

import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/api/answers")
public class ApiAnswerController {

    @Value("$error.not.supported")
    private String notFormattedError;

    @Autowired
    private AnswerService answerService;

    private static final Logger logger = getLogger(ApiAnswerController.class);



}
