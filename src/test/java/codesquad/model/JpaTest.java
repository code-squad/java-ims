package codesquad.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void test() {
		User user = new User();
		user.setId("qwerty");
		user.setName("ㅂㅈㄷㄱ쇼");
		user.setPassword("1234");
		
		entityManager.persist(user);
		
		User findUser = userRepository.findOne("qwerty");
		assertTrue(findUser.matchingPassword("1234"));
		assertEquals(findUser.getName(), "ㅂㅈㄷㄱ쇼");
	}
}
