package codesquad.dto;

public class LabelDto {
    private String label;

    private UserDto writer;

    public LabelDto() {
    }

    public LabelDto(String label, UserDto writer) {
        this.label = label;
        this.writer = writer;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public UserDto getWriter() {
        return writer;
    }

    public void setWriter(UserDto writer) {
        this.writer = writer;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        result = prime * result + ((writer == null) ? 0 : writer.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LabelDto other = (LabelDto) obj;
        if (label == null) {
            if (other.label != null)
                return false;
        } else if (!label.equals(other.label))
            return false;
        if (writer == null) {
            if (other.writer != null)
                return false;
        } else if (!writer.equals(other.writer))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LabelDto [label=" + label + ", writer=" + writer + "]";
    }
}
