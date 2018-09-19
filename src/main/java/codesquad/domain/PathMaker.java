package codesquad.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Random;

@Component
public class PathMaker {
    private static final Logger log = LoggerFactory.getLogger(PathMaker.class);

    @Value("${rootLocation}")
    private String rootLocation; // target\files

    @PostConstruct
    public void init() {
        log.debug("this.location : {}", this.rootLocation);
    }

    public PathMaker() {
    }

    private String makeRandomNumber() {
        return String.valueOf(Math.abs(new Random().nextLong()));
    }

    public String makeRandomDirPath() {
        // \98762347
        return File.separator + makeRandomNumber();
    }

    public String getRootLocation() {
        return rootLocation;
    }

    public String getFullPath(String filename, String dirPath) {
        // target\files\[random number]\sample.txt
        log.debug("PathMaker rootLocation : {}", rootLocation);
        return rootLocation + dirPath + File.separator + filename;
    }
}
