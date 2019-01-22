package codesquad.domain.issue;

import codesquad.domain.user.User;
import support.domain.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class File extends AbstractEntity {

    @Column
    private String originalName;

    @Column
    private String savedName;

    @Column
    private String location;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_file_uploader"))
    private User uploader;

    public File() {
    }

    public File(String originalName, String savedName, String location, User uploader) {
        this(0L, originalName, savedName, location, uploader);
    }

    public File(long id, String originalName, String savedName, String location, User uploader) {
        super(id);
        this.originalName = originalName;
        this.savedName = savedName;
        this.location = location;
        this.uploader = uploader;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getSavedName() {
        return savedName;
    }

    public void setSavedName(String savedName) {
        this.savedName = savedName;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        File file = (File) o;
        return Objects.equals(originalName, file.originalName) &&
                Objects.equals(savedName, file.savedName) &&
                Objects.equals(uploader, file.uploader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), originalName, savedName, uploader);
    }

    @Override
    public String toString() {
        return "File{" +
                "originalName='" + originalName + '\'' +
                ", savedName='" + savedName + '\'' +
                ", uploader=" + uploader +
                '}';
    }
}