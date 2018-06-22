package codesquad.domain;

public enum Label {

    BUG(1, "Bug"),
    DEPLOYMENT(2, "Deployment"),
    INSPECTION(3, "Inspection"),
    SCHEDULE(4, "Schedule"),
    FEATURES(5, "Features");

    private long id;
    private String name;

    private Label(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public long getId() {
        return id;
    }

    public static Label findLabel(long id){
        for(Label label: Label.values()){
            if(label.id == id) {
                return label;
            }
        }
        return null;
    }
}
