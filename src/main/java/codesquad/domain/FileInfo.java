package codesquad.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Entity
public class FileInfo {
    private static final Logger log =  LoggerFactory.getLogger(FileInfo.class);

//    @Transient
//    public static final Path rootLocation = Paths.get("target/files/");

    @Id @GeneratedValue
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(nullable = false)
    @Convert(converter = PathConverter.class)
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

    public FileInfo(MultipartFile file, Path dirPath) {
        this.name = getFileName(file);
        this.path = dirPath.resolve(getFileName(file)); // target/files/3456789123/img001.jpg
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

    private static String getFileName(MultipartFile file) {
        int index = file.getOriginalFilename().lastIndexOf("\\"); // C:/image/img001.jpg
        return file.getOriginalFilename().substring(index+1); // img001.jpg
    }
}
