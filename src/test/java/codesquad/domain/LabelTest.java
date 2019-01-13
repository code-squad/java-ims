package codesquad.domain;

import org.junit.Test;
import support.test.BaseTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static codesquad.domain.IssueTest.ISSUE1;

public class LabelTest extends BaseTest {
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

    @Test
    public void setIssues_already_exist() {
        LABEL2.setIssues(new ArrayList<>(Arrays.asList(ISSUE1)));
        LABEL2.setIssues(ISSUE1);

        softly.assertThat(LABEL2.getIssues().contains(ISSUE1)).isFalse();
    }

    @Test
    public void setIssues() {
        LABEL1.setIssues(ISSUE1);

        softly.assertThat(LABEL1.getIssues().contains(ISSUE1)).isTrue();
    }
}
