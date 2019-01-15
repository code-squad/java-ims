package codesquad.domain;

public class AttachmentDummy extends Attachment{

    public AttachmentDummy() {

    }

    public AttachmentDummy(String originFileName, String targetFileName, String path) {
        super(originFileName, targetFileName, path);
    }

    @Override
    public boolean isDummyAttachment() {
        return true;
    }
}