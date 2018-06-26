package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum IssueStatus {
    @JsonProperty("Open")
    OPEN,
    @JsonProperty("Closed")
    CLOSED;

    public static boolean isClosed(IssueStatus status) {
        return status == CLOSED;
    }

    public String getStatus() {
        return name();
    }
}
