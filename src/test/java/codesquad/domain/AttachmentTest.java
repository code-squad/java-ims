package codesquad.domain;

import org.junit.Test;
import support.test.BaseTest;
import java.util.ArrayList;
import java.util.List;

public class AttachmentTest extends BaseTest {

    private List<String> suffixes = new ArrayList<>();


    @Test
    public void obatainSuffixTest_성공() {
        softly.assertThat(Attachment.obtainSuffix("abc.jpg")).isEqualTo("jpg");
    }

}
