package codesquad.domain;

import support.domain.Entity;

public enum ContentType {
    ISSUE("issue", "issues"),
    USER("user", "users"),
    MILESTONE("milestone", "milestones"),
    COMMENT("comment", "comments");

    private String name;
    private String multipleName;

    ContentType(String name, String multipleName) {
        this.name = name;
        this.multipleName = multipleName;
    }

    public static String getContentName(ContentType contentType) {
        return contentType.name;
    }

    public static String getMultipleContentName(ContentType contentType) {
        return contentType.multipleName;
    }
}
