package support.domain;

public enum Entity {
    ISSUE("issue");

    private String name;

    Entity(String name) {
        this.name = name;
    }

    public static String getEntityName(Entity entity) {
        return entity.name;
    }
}
