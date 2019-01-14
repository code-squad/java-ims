//package codesquad.domain;
//
//import support.domain.AbstractEntity;
//
//import javax.persistence.*;
//
//@Entity
//@Table (name = "labelManager")
//public class LabelManager extends AbstractEntity {
//    @ManyToOne
//    @JoinColumn(name = "issue_id")
//    private Issue issue;
//
//    @ManyToOne
//    @JoinColumn(name = "label_id")
//    private Label label;
//
//    public LabelManager(){};
//
//    public LabelManager(Issue issue, Label label) {
//        this.issue = issue;
//        this.label = label;
//    }
//
//    public Label getLabel() {
//        return label;
//    }
//}
