package support.test;


import codesquad.domain.User;

public class UserFixture {
    public static final User DOBY = new User.UserBuilder("doby", "password", "lkhlkh23").setId(3L).build();

    public static final User JAVAJIGI = new User.UserBuilder("javajigi", "password", "자바지기").setId(1L).build();
}
