package codesquad.service;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentServiceTest {
    private static final Logger log = LoggerFactory.getLogger(AttachmentServiceTest.class);

    @Mock
    AttachmentRepository attachmentRepository;

    @Mock
    IssueRepository issueRepository;

    @InjectMocks
    AttachmentService attachmentService = new AttachmentService();

    @Test
    public void newName() {
        String oldName = "test-name";
        String newName = attachmentService.makeNewName(oldName);

        log.debug("newName : {}", newName);

        assertNotEquals(oldName, newName);
    }

    @Test
    public void makeFile() {
        String example = "Convert Java String";
        byte[] bytes = example.getBytes();

        File madeFile = attachmentService.makeFile(bytes, "test-name");
        assertThat(madeFile.getName(), is("test-name"));
    }

    @Test
    public void makeInfo() {
//        AttachmentService attachmentService = new AttachmentService();
        String example = "Convert Java String";
        byte[] bytes = example.getBytes();

        File madeFile = attachmentService.makeFile(bytes, "test-name");
        assertThat(madeFile.getName(), is("test-name"));

        String fileName = "test-name";
        String fileType = "type";
        Issue testIssue = new Issue("test subject", "test comment");
        Attachment testAttachment = new Attachment(fileName, fileType, testIssue);

        // 파일 정보 생성후 디비 저장
        when(issueRepository.findById(0L)).thenReturn(java.util.Optional.ofNullable(testIssue));
        when(attachmentService.saveFile(madeFile, fileType, 0L)).thenReturn(testAttachment);
        Attachment attachment = attachmentService.saveFile(madeFile, fileType, 0L);
        assertThat(attachment, is(testAttachment));
    }
}
