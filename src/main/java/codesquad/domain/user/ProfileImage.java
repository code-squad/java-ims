package codesquad.domain.user;

import codesquad.DataFormatException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProfileImage {
    public static final ProfileImage DEFAULT_IMAGE = new DefaultImage();
    private static final String EXTENSION_FORMAT = "(jpg|png|gif|jpeg)$";
    private static boolean isDefault = false;

    @Column
    private String originalFileName;

    @Column
    private String savedFileName;

    @Column
    private String extension;


    public ProfileImage() {
    }

    public ProfileImage(String originalFileName, String savedFileName, String extension) {
        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;
        this.extension = extension;
    }

    public static ProfileImage of(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            isDefault = true;
            return new DefaultImage();
        }
        String originalFileName = file.getOriginalFilename();
        return new ProfileImage(originalFileName, originalFileName, isValidExtension(originalFileName));
    }

    private static String isValidExtension(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName);
        if (!extension.matches(EXTENSION_FORMAT)) {
            throw new DataFormatException();
        }
        return extension;
    }

    public boolean isDefaultImage() {
        return isDefault;
    }



    private static class DefaultImage extends ProfileImage {
        public static final String DEFAULT_FILE_NAME = "default";
        public static final String DEFAULT_EXTENSION = "jpg";

        public DefaultImage() {
            super(DEFAULT_FILE_NAME, DEFAULT_FILE_NAME, DEFAULT_EXTENSION);
        }
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getSavedFileName() {
        return savedFileName;
    }

    public void setSavedFileName(String savedFileName) {
        this.savedFileName = savedFileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
