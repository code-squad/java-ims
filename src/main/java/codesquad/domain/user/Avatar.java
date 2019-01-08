package codesquad.domain.user;

import codesquad.DataFormatErrorException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import support.domain.FileEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Avatar implements FileEntity {
    private static final String EXTENTION_FORMAT = "((jpg|png|jpeg)$)";
    public static final Avatar DEFAULT_AVATAR = new DefaultAvatar();

    @Column
    private String originalFileName;

    @Column
    private String savedFileName;

    @Column
    private String extention;

    public Avatar() {
    }

    private Avatar(String originalFileName, String savedFileName, String extention) {
        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;
        this.extention = extention;
    }

    public static Avatar of(MultipartFile file) {
        if (file == null || file.isEmpty()) return new DefaultAvatar();
        String originalName = file.getOriginalFilename();
        return new Avatar(originalName, originalName, isValidExtention(originalName));
    }

    private static String isValidExtention(String originalFileName) {
        String extention = FilenameUtils.getExtension(originalFileName).toLowerCase();
        if (!extention.matches(EXTENTION_FORMAT)) throw new DataFormatErrorException();
        return extention;
    }

    public String getCurrentFileNameWithExtention() {
        return savedFileName + "." + extention;
    }

    public boolean isDefaultAvatar() {
        return false;
    }

    @Override
    public String getOriginalFilename() {
        return originalFileName;
    }

    @Override
    public void changeFilename(String savedFileName) {
        this.savedFileName = savedFileName;
    }

    private static class DefaultAvatar extends Avatar {
        public static final String DEFAULT_FILE_NAME = "default";
        public static final String DEFAULT_EXTENTION = "jpeg";

        public DefaultAvatar() {
            super(DEFAULT_FILE_NAME, DEFAULT_FILE_NAME, DEFAULT_EXTENTION);
        }

        @Override
        public boolean isDefaultAvatar() {
            return true;
        }
    }
}
