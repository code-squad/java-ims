package codesquad.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Entity
public class FileInfo {
    private static final Logger log = LoggerFactory.getLogger(FileInfo.class);

    @Transient
    private final String SUFFIX = "0";

    @Id
    @GeneratedValue
    private Long id;

    private Long issueId;

    @Column(length = 20, nullable = false, unique = true)
    private String name; // img001.jpg

    @Column(nullable = false)
    @Convert(converter = PathConverter.class)
    private Path path; // target/files/[randomNumber]/img001.jpg

    public FileInfo() {
    }

    public FileInfo(String name) {
        this.name = name;
    }

    public FileInfo(String name, Path path) {
        this.name = name;
        this.path = path;
    }

    public FileInfo(MultipartFile file, Path dirPath, Long issueId) {
        this.issueId = issueId;
        this.name = getFilename(file); // img001.jpg
        this.path = dirPath.resolve(getFilename(file)); // target/files/[randomNumber]/img001.jpg
        log.debug("path : {}", path);
        log.debug("name : {}", name);
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public void addNumberToFilename() {
        int index = name.lastIndexOf(".");
        String extension = name.substring(index);
        log.debug("extension : {}", extension);

        name = name.substring(0, index) + SUFFIX + extension;
        log.debug("updated name : " + name);

        updateFilenameOnPath(name);
    }

    private static String getFilename(MultipartFile file) {
        int index = file.getOriginalFilename().lastIndexOf("\\"); // C:/image/img001.jpg
        return file.getOriginalFilename().substring(index + 1); // img001.jpg
    }

    private void updateFilenameOnPath(String name) {
        this.path = getDirPath().resolve(name);
        log.debug("updated Path : {}", path);
    }

    public Path getDirPath() {
        Path test = Paths.get(path.toString().substring(0, path.toString().lastIndexOf("\\")));
        log.debug("path : {}", test.toString());
        return test;
    }
}
