package codesquad.domain;

import codesquad.ApplicationConfigurationProp;
import org.springframework.web.multipart.MultipartFile;
import support.converter.FileNameConverter;

import javax.persistence.Embeddable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Embeddable
public class Attachment {

    private static final String SPLIT_STANDARD = ".";

    private String originFileName;
    private String targetFileName;
    private String path;

    public Attachment() {

    }

    public Attachment(String originFileName, String targetFileName, String path) {
        this.originFileName = originFileName;
        this.targetFileName = targetFileName;
        this.path = path;
    }

    public String getTargetFileName() {
        return targetFileName;
    }

    public void setTargetFileName(String targetFileName) {
        this.targetFileName = targetFileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isDummyAttachment() {
        return false;
    }

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public Attachment createAttachment(MultipartFile multipartFile) throws IOException {
        InputStream is = multipartFile.getInputStream();
        Files.copy(is, createPath(path, targetFileName), StandardCopyOption.REPLACE_EXISTING);

        return this;
    }

    public Path createPath(String path, String targetFileName) {
        return Paths.get(String.format("%s/%s", path, targetFileName));
    }

    public static Attachment of(MultipartFile multipartFile, String path, List<String> suffixes) {
        String originFileName = multipartFile.getOriginalFilename();
        String targetFileName = FileNameConverter.convert(originFileName);

        return new Attachment(originFileName, targetFileName, path);
    }

    public static boolean extensionCheck(MultipartFile multipartFile, ApplicationConfigurationProp applicationConfigurationProp) {
        String fileName = multipartFile.getOriginalFilename();
        if(!fileName.contains(SPLIT_STANDARD)) {
            System.out.println("닷이 없어!");
            return false;
        }

        List<String> suffixes = applicationConfigurationProp.getSuffix();
        if(!suffixes.contains(obtainSuffix(fileName))) {
            System.out.println("확장자가 아니야");
            System.out.println(suffixes.toString()+"~~~~");
            return false;
        }

        return true;
    }

    public static String obtainSuffix(String fileName) {
        String[] splited = fileName.split("\\.");
        System.out.println("확장자는 " + splited[splited.length - 1]);
        return splited[splited.length - 1];
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "originFileName='" + originFileName + '\'' +
                ", targetFileName='" + targetFileName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
