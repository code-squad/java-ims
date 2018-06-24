package codesquad.domain;

public enum IssueStatus {
    OPEN, CLOSED;

    public static boolean isClosed(IssueStatus status) {
        return status == CLOSED;
    }
}
