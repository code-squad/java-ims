package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import java.time.LocalDateTime;

public class Milestone extends AbstractEntity {

    @Column(nullable = false)
    private LocalDateTime start;

    @Column(nullable = false)
    private LocalDateTime end;

    @Embedded
    private Issues issues;

    public Milestone() {

    }

    public Milestone(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Milestone{" +
                "id=" + getId() +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
