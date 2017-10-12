package io.github.cepr0;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Cepro, 2017-10-11
 */
@DataJpaTest
@Transactional
public class UserRepoTest extends BaseTest {
	
	@Autowired protected UserRepo userRepo;
	
	@Before
	public void setUp() throws Exception {
		// given
		userRepo.save(Arrays.asList(user1, user2, user3));
	}
	
	@Test
	public void findByNameContainingIgnoreCaseTest() throws Exception {
		// when
		List<User> users = userRepo.findByNameContainingIgnoreCase("user");
		
		// then
		assertThat(users).isNotNull();
		assertThat(users).hasSize(3);
		assertThat(users.stream().map(User::getName).collect(Collectors.toList())).containsOnly(
				USER_NAME_1,
				USER_NAME_2,
				USER_NAME_3
		);
		
		// when
		users = userRepo.findByNameContainingIgnoreCase("1");
		
		// then
		assertThat(users).isNotNull();
		assertThat(users).hasSize(1);
		assertThat(users.stream().map(User::getName).collect(Collectors.toList())).containsOnly(
				USER_NAME_1
		);
	}
	
	@Test
	public void findByNameAndEmailTest() throws Exception {
		// when
		Page<User> usersPage = userRepo.findByNameOrEmail("1", new PageRequest(0, 20));
		
		// then
		assertThat(usersPage).isNotNull();
		List<User> users = usersPage.getContent();
		assertThat(users).hasSize(1);
		assertThat(users.stream().map(User::getName).collect(Collectors.toList())).containsOnly(
				USER_NAME_1
		);
		
		// when
		usersPage = userRepo.findByNameOrEmail("@test", new PageRequest(0, 20));
		
		// then
		assertThat(usersPage).isNotNull();
		users = usersPage.getContent();
		assertThat(users).hasSize(3);
		assertThat(users.stream().map(User::getName).collect(Collectors.toList())).containsOnly(
				USER_NAME_1,
				USER_NAME_2,
				USER_NAME_3
		);
	}
}