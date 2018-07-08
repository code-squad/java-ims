package codesquad.dto;

import org.springframework.web.multipart.MultipartFile;

// TODO 아무나 다운로드 받을 수 있는 경우
// TODO 해당 댓글의 요청인 경우도 구분?
// TODO 원래 제목 저장하지 않는다
public class AttachmentDto {
    MultipartFile file;

    public AttachmentDto(MultipartFile file) {
        this.file = file;
    }

    public AttachmentDto() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
