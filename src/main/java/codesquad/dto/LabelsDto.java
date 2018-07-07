package codesquad.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LabelsDto {
    private List<Pair> labels;

    public LabelsDto() {
        labels = new ArrayList<>();
    }

    public LabelsDto(List<Pair> labels) {
        this.labels = labels;
    }

    public List<Pair> getLabels() {
        return labels;
    }

    public void setLabels(List<Pair> labels) {
        this.labels = labels;
    }

    public void addLabel(long id, String subject) {
        labels.add(new Pair(id, subject));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabelsDto labelsDto = (LabelsDto) o;
        return Objects.equals(labels, labelsDto.labels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(labels);
    }

    @Override
    public String toString() {
        return "LabelsDto{" +
                "labels=" + labels +
                '}';
    }
}
