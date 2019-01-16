package codesquad.domain;

import org.junit.Before;
import org.junit.Test;
import support.test.BaseTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static codesquad.domain.IssueTest.ISSUE1;
import static codesquad.domain.IssueTest.ISSUE3;
import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;

public class LabelTest extends BaseTest {
    public static final List<Label> LABELS1 = new ArrayList<>();
    public static final List<Label> LABELS2 = new ArrayList<>();
    public static final List<Label> LABELS = new ArrayList<>();
    public static final Label LABEL1 = new Label(1, "스포츠", JAVAJIGI);
    public static final Label LABEL2 = new Label(2, "정치", JAVAJIGI);
    public static final Label LABEL3 = new Label(3, "연예", JAVAJIGI);
    public static final Label LABEL4 = new Label(4, "라벨4", SANJIGI);
    public static final Label LABEL5 = new Label(5, "라벨5", SANJIGI);
    public static final Label LABEL6 = new Label(6, "라벨6", SANJIGI);
    public static final Label UPDATEDLABEL2 = new Label(7, "정치업데이트", JAVAJIGI);
    public static final Label UPDATEDLABEL5 = new Label(8, "라벨5업데이트", JAVAJIGI);
    public static final Label UPDATEDLABEL6 = new Label(9, "라벨6업데이트", SANJIGI);

    static {
        LABELS1.add(LABEL1);

        LABELS2.add(LABEL2);
        LABELS2.add(LABEL3);

        LABELS.add(LABEL1);
        LABELS.add(LABEL3);
        LABELS.add(LABEL4);
    }

    @Before
    public void setUp() throws Exception {
        LABEL6.setIssues(new ArrayList<>(Arrays.asList(ISSUE1)));
        LABEL5.setIssues(new ArrayList<>(Arrays.asList(ISSUE3)));
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
