package io.github.cepr0;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Cepro, 2017-10-11
 */
@WebMvcTest
@EnableSpringDataWebSupport
public class RootControllerTest extends BaseTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserRepo userRepo;
	
	private Page<User> mockFindByNameOrEmail(List<User> users, int pageNum) {
		Pageable pageable = new PageRequest(pageNum, 5, new Sort(Sort.Direction.ASC, "name"));
		Page<User> userPage = new PageImpl<>(users, pageable, 7);
		when(userRepo.findByNameOrEmail("user", pageable)).thenReturn(userPage);
		return userPage;
	}
	
	@Test
	public void usersTest() throws Exception {
		
		// given
		Page<User> userPage = mockFindByNameOrEmail(Arrays.asList(user1, user2, user3, user4, user5), 0);
		
		// when
		ResultActions result = mvc.perform(get("/?q=user")); // first page
		
		// then
		result//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("users"))
				.andExpect(model().attributeExists("query"))
				.andExpect(model().attribute("query", is("user")))
				.andExpect(model().attributeExists("totalPages"))
				.andExpect(model().attribute("totalPages", is(2)))
				.andExpect(model().attributeExists("current"))
				.andExpect(model().attribute("current", is(0)))
				.andExpect(model().attributeExists("users"))
				.andExpect(model().attribute("users", is(userPage)))
				.andExpect(model().attribute("users", hasProperty("content", hasSize(5))))
				.andExpect(model().attribute("users", contains(user1, user2, user3, user4, user5)))
				.andReturn();
		
		// given
		userPage = mockFindByNameOrEmail(Arrays.asList(user6, user7), 1);
		
		// when
		result = mvc.perform(get("/?q=user&page=1")); // second page
		
		// then
		result//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("users"))
				.andExpect(model().attributeExists("query"))
				.andExpect(model().attribute("query", is("user")))
				.andExpect(model().attributeExists("totalPages"))
				.andExpect(model().attribute("totalPages", is(2)))
				.andExpect(model().attributeExists("current"))
				.andExpect(model().attribute("current", is(1)))
				.andExpect(model().attributeExists("users"))
				.andExpect(model().attribute("users", is(userPage)))
				.andExpect(model().attribute("users", hasProperty("content", hasSize(2))))
				.andExpect(model().attribute("users", contains(user6, user7)))
				.andReturn();
	}
	
	@Test
	public void editTest() throws Exception {
		// given
		when(userRepo.findOne(1)).thenReturn(user1);
		
		// when
		ResultActions result = mvc.perform(get("/user/{id}", 1));
		
		// then
		result//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("edit"))
				.andExpect(model().attributeExists("user"))
				.andExpect(model().attribute("user", is(user1)))
				.andReturn();
	}
	
	@Test
	public void saveTest() throws Exception {
		// given
		when(userRepo.save(user1)).thenReturn(user1);
		
		// when
		ResultActions result = mvc.perform(post("/user")
				.param("name", user1.getName())
				.param("email", user1.getEmail())
				.param("password", user1.getPassword())
				.param("role", user1.getRole().toString())
				//.param("registered", user1.getRegistered().format(ofLocalizedDateTime(SHORT)))
				.param("enabled", user1.getEnabled().toString())
		);
		
		// then
		result//.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/?q=" + user1.getName()))
				.andExpect(view().name("redirect:/?q=User1"))
				.andReturn();
	}
	
	@Test
	public void deleteTest() throws Exception {
		// given
		doNothing().when(userRepo).delete(1);
		
		// when
		ResultActions result = mvc.perform(get("/user/delete/{id}", 1));
		
		// then
		result//.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"))
				.andExpect(view().name("redirect:/"))
				.andReturn();
	}
	
	@Test
	public void createTest() throws Exception {
		// given
		
		// when
		ResultActions result = mvc.perform(get("/user"));
		
		// then
		result//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("edit"))
				.andExpect(model().attributeExists("user"))
				.andExpect(model().attribute("user", hasProperty("name", isEmptyOrNullString())))
				.andReturn();
	}
}