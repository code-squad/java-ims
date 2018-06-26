package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum IssueStatus {
    @JsonProperty("Open")
    OPEN,

    @JsonProperty("Closed")
    CLOSED;

    @Override
    public String toString() {
        if (this == OPEN) {
            return "Open";
        }
        return "Closed";
    }

    public boolean isOpen() {
        return this == OPEN;
    }
}
