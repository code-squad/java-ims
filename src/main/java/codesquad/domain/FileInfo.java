package codesquad.domain;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileInfo {
    public static final Path rootLocation = Paths.get("target/files/");

    private String name;
    private Path path; // target/files/sample.txt

    public FileInfo() {
    }

    public FileInfo(String name) {
        this.name = name;
    }

    public FileInfo(String name, Path path) {
        this.name = name;
        this.path = path;
    }

    public FileInfo(MultipartFile file) {
        this.name = file.getName();
        this.path = rootLocation.resolve(file.getOriginalFilename());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
