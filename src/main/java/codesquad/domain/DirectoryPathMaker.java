package codesquad.domain;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class DirectoryPathMaker {
    private Path rootLocation = Paths.get("target/files/");

    public DirectoryPathMaker() {
    }

    public Path makePath() {
        return rootLocation.resolve(String.valueOf(Math.abs(new Random().nextLong())));
    }
}
