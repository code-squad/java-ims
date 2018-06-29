package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;
import java.io.File;
import java.util.UUID;

@Entity
public class Attachment extends AbstractEntity{

    private static final String path = "C:\\Users\\Kang\\java-ims\\src\\main\\resources\\uploadFile";

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false, unique = true)
    private String savedName;

    private long size;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_attachment_to_saver"))
    private User saver;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_attachment_to_comment"))
    private Comment comment;

    public Attachment(){
    }

    public Attachment(String originalName, long size, User saver) {
        this.originalName = originalName;
        this.savedName = randomName(originalName);
        this.size = size;
        this.saver = saver;
    }

    public File save(){
        return new File(path + File.separator + savedName);
    }

    private String randomName(String originalName) {
       return UUID.randomUUID().toString().replace("-", "") + parseExtension(originalName);
    }

    private String parseExtension(String file){
        return file.substring(file.lastIndexOf("."));
    }
}
