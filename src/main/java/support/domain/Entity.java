package support.domain;

public enum Entity {
    ISSUE("issue", "issues"),
    USER("user", "users");

    private String name;
    private String multipleName;

    Entity(String name, String multipleName) {
        this.name = name;
        this.multipleName = multipleName;
    }

    public static String getEntityName(Entity entity) {
        return entity.name;
    }

    public static String getMultipleEntityName(Entity entity) {
        return entity.multipleName;
    }
}
