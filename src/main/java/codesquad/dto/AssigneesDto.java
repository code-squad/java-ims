package codesquad.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AssigneesDto {
    private List<Pair> assignees;

    public AssigneesDto() {
        assignees = new ArrayList<>();
    }

    public AssigneesDto(List<Pair> milestones) {
        assignees = milestones;
    }

    public List<Pair> getAssignees() {
        return assignees;
    }

    public void setMilestones(List<Pair> assignees) {
        this.assignees = assignees;
    }

    public void addMilestones(long id, String subject) {
        assignees.add(new Pair(id, subject));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssigneesDto that = (AssigneesDto) o;
        return Objects.equals(assignees, that.assignees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignees);
    }

    @Override
    public String toString() {
        return "AssigneesDto{" +
                "assignees=" + assignees +
                '}';
    }

    public void addUsers(long id, String name) {
        assignees.add(new Pair(id, name));
    }
}
