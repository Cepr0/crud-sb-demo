package io.github.cepr0;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Cepro, 2017-10-11
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public abstract class BaseTest {
	
	private static final String PSW = "123456";
	final String USER_NAME_1 = "User1";
	final String USER_NAME_2 = "User2";
	final String USER_NAME_3 = "User3";
	
	User user1 = new User(USER_NAME_1, "user1@test.com", PSW);
	User user2 = new User(USER_NAME_2, "user2@test.com", PSW);
	User user3 = new User(USER_NAME_3, "user3@test.com", PSW);
	User user4 = new User("User4", "user4@test.com", PSW);
	User user5 = new User("User5", "user5@test.com", PSW);
	User user6 = new User("User6", "user6@test.com", PSW);
	User user7 = new User("User7", "user7@test.com", PSW);
}
