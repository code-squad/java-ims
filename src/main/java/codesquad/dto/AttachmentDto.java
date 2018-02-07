package codesquad.dto;

import codesquad.domain.Attachment;

public class AttachmentDto {
    private String originalFileName;
    private String savedFileName;

    public AttachmentDto(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public AttachmentDto() {
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

    public Attachment toAttachment() {
        return new Attachment(originalFileName, savedFileName);
    }
}
