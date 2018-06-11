package codesquad.domain;

public enum IssueStatus {
    OPEN, CLOSED;

    @Override
    public String toString() {
        if (this == OPEN) {
            return "Open";
        }
        return "Closed";
    }
}
