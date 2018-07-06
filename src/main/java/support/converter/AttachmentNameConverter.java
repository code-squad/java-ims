package support.converter;

import java.util.UUID;

public class AttachmentNameConverter {

    public static String convertName(String originAttachmentName) {
        return UUID.randomUUID() + "_" + originAttachmentName;
    }
}
