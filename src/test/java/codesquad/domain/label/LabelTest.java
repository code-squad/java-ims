package codesquad.domain.label;

public class LabelTest {
    public static final LabelBody LABEL_BODY = new LabelBody("Label1", "Label1에 대한 설명입니다");
    public static final Label LABEL = new Label(LABEL_BODY);
    public static final LabelBody LABEL_BODY2 = new LabelBody("Label2", "Label2에 대한 설명입니다");
    public static final Label LABEL2 = new Label(LABEL_BODY);
    public static final LabelBody LABEL_BODY3 = new LabelBody("Label3", "Label3에 대한 설명입니다");
    public static final Label LABEL3 = new Label(LABEL_BODY);
}