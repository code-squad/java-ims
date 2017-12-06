package codesquad.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import codesquad.exception.InvalidUserException;

public class UserTest {
	User user;
	User updateUser;

	@Before
	public void before() {
		user = new User();
		user.setId("abcshc");
		user.setName("소희철");
		user.setPassword("1234");
		updateUser = new User();
		updateUser.setId("abcshc");
		updateUser.setName("홍길동");
		updateUser.setPassword("2345");
	}

	@Test
	public void testMatchingPassword() {
		assertTrue(user.matchingPassword("1234"));
	}

	@Test
	public void testUpdate() {
		user.update(updateUser);
		assertTrue(user.matchingPassword("2345"));
		assertEquals(user.getName(), "홍길동");
	}

	@Test(expected = InvalidUserException.class)
	public void testUpdateException() {
		updateUser.setId("defshc");
		user.update(updateUser);
	}
}