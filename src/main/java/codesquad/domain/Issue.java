package codesquad.domain;

public class Issue {
    String subject;
    String comment;

    public Issue(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
