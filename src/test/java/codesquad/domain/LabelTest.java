package codesquad.domain;

import java.util.ArrayList;
import java.util.List;

public class LabelTest {
    public static final List<Label> LABELS1 = new ArrayList<>();
    public static final List<Label> LABELS2 = new ArrayList<>();
    public static final List<Label> LABELS = new ArrayList<>();
    public static final Label LABEL1 = new Label(1, "스포츠");
    public static final Label LABEL2 = new Label(2, "정치");
    public static final Label LABEL3 = new Label(3, "연예");
    public static final Label LABEL4 = new Label(4, "라벨4");
    public static final Label LABEL5 = new Label(5, "라벨5");
    public static final Label LABEL6 = new Label(6, "라벨6");
    public static final Label UPDATEDLABEL2 = new Label(7, "정치업데이트");

    static {
        LABELS1.add(LABEL1);

        LABELS2.add(LABEL2);
        LABELS2.add(LABEL3);

        LABELS.add(LABEL4);
        LABELS.add(LABEL5);
        LABELS.add(LABEL6);
    }
}
