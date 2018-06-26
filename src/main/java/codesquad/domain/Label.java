package codesquad.domain;

public enum Label {
    Backlog(1, "Backlog"),
    Planning(2, "Planning"),
    Ready(3, "Ready"),
    Working(4, "Workgin");

    private int id;
    private String name;


    Label(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
