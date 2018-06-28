package codesquad.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@PropertySource(value = "classpath:application.properties")
public class Attachment extends AbstractEntity {
    private static final Logger logger = LoggerFactory.getLogger(Attachment.class);

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey)
    private Issue issue;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey)
    private User writer;

    @Size(min = 1, max = 20)
    private String originalFileName;

    private String hashedFileName;

    private String filePath;


    public Attachment() {
    }

    public Attachment(String originalFileName) {
        this.originalFileName = originalFileName;
        this.hashedFileName = createHashedName();
    }


    public Attachment(User writer, Issue issue, String originalFileName) {
        this.writer = writer;
        this.issue = issue;
        this.originalFileName = originalFileName;
        this.hashedFileName = createHashedName();
    }

    private String createHashedName() {
        return UUID.randomUUID() + "_" + originalFileName;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                ", issue=" + issue +
                ", writer=" + writer +
                ", originalFileName='" + originalFileName + '\'' +
                ", hashedFileName='" + hashedFileName + '\'' +
                '}';
    }


    public String generateHashedPath(String location) {
        filePath = location + createHashedName();
        return filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }
}
