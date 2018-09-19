package codesquad.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.nio.file.Path;

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
    private String dirPath; // /[random number]

    public FileInfo() {
    }

    public FileInfo(String name, String dirPath) {
        this.name = name;
        this.dirPath = dirPath;
    }

    public FileInfo(MultipartFile file, String dirPath, Long issueId) {
        this.issueId = issueId;
        this.name = getFilename(file); // img001.jpg
        this.dirPath = dirPath;
        log.debug("dirPath : {}", this.dirPath);
        log.debug("name : {}", name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
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
    }

    private static String getFilename(MultipartFile file) {
        int index = file.getOriginalFilename().lastIndexOf("\\"); // C:/image/img001.jpg
        return file.getOriginalFilename().substring(index + 1); // img001.jpg
    }

    public String getFullPath(PathMaker pathMaker) {
        return pathMaker.getFullPath(name, dirPath);
    }

    public String getDirPathWithRoot(PathMaker pathMaker) {
        log.debug("dir path with root : {} ", pathMaker.getRootLocation() + dirPath);
        return pathMaker.getRootLocation() + dirPath + File.separator; // target\files\[random number]
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "SUFFIX='" + SUFFIX + '\'' +
                ", id=" + id +
                ", issueId=" + issueId +
                ", name='" + name + '\'' +
                ", dirPath='" + dirPath + '\'' +
                '}';
    }
}
