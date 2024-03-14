package in.jeani.krazy.config.security;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import in.jeani.krazy.models.User;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class AppTest
{
	@Autowired
	private MockMvc mockMvc;
	
	private static CopyOnWriteArrayList<User> createdUsers = new CopyOnWriteArrayList<>();
	
	@Test
	@Order(0)
	public void testApp() throws Exception
    {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/home"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Home sweet home!!!")));
    }
	
	@Test
	@Order(1)
	public void testCreateUser() throws Exception {
		List<User> newUsers = getNewUsersForCreation();
		for (User user : newUsers) {
			MvcResult mvcResult = this.mockMvc.perform(
					MockMvcRequestBuilders.post("/api/create")
					.contentType(MediaType.APPLICATION_JSON)
					.content(getJsonifiedData(user)))
					.andDo(MockMvcResultHandlers.print())
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andReturn();
			String responseJson = mvcResult.getResponse().getContentAsString();
			User createdUser = getUserObj(responseJson);
			createdUsers.add(createdUser);
			assertTrue(createdUser.getId() != null);
			
			user.setAddress(user.getAddress() + " updated");
		}
		
    }
	
	@Test
	@Order(2)
	public void testFindByFirstName() throws Exception
    {
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/find/by/name/first/first"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		String responseJson = mvcResult.getResponse().getContentAsString();
		List<?> items = getUserObjList(responseJson);
		assertTrue(items.size() == 10);
    }

	@Test
	@Order(2)
	public void testFindByLastName() throws Exception
    {
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/find/by/name/last/last"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		String responseJson = mvcResult.getResponse().getContentAsString();
		List<?> items = getUserObjList(responseJson);
		assertTrue(items.size() == 10);
    }
	@Test
	@Order(2)
	public void testFindByID() throws Exception
    {
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/find/by/id/1"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		String responseJson = mvcResult.getResponse().getContentAsString();
		User existingUser = getUserObj(responseJson);
		assertTrue(existingUser.getId() == 1);
    }
	
	@Test
	@Order(2)
	public void testFindAll() throws Exception
    {
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/fetch/all"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		String responseJson = mvcResult.getResponse().getContentAsString();
		List<?> items = getUserObjList(responseJson);
		assertTrue(items.size() == 10);
    }
	
	@Test
	@Order(3)
	public void testUpdateUser() throws Exception {
		for (User user : createdUsers) {
			// Update the address with sample test
			user.setAddress(user.getAddress() + " updated for test");
			this.mockMvc.perform(
					MockMvcRequestBuilders.put("/api/update")
					.contentType(MediaType.APPLICATION_JSON)
					.content(getJsonifiedData(user)))
					.andDo(MockMvcResultHandlers.print())
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString("updated for test")))
					.andReturn();
		}
	}
	
	@Test
	@Order(4)
	public void testDeleteUser() throws Exception {
		for (User user : createdUsers) {
			// Update the address with sample test
			user.setAddress(user.getAddress() + " updated for test");
			this.mockMvc.perform(
					MockMvcRequestBuilders.delete("/api/delete/" + user.getId()))
					.andDo(MockMvcResultHandlers.print())
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().string("true"))
					.andReturn();
			
		}
	}
	
	private List<User> getUserObjList(String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<User> users = mapper.readValue(json, List.class);
		return users;
	}
	private User getUserObj(String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.readValue(json, User.class);
		return user;
	}
	private String getJsonifiedData(User user) throws IOException {
		String json = null;
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		json = ow.writeValueAsString(user);
		return json;
	}
	private List<User> getNewUsersForCreation() {
		List<User> users = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			users.add(getUserObj(i));
		}
		return users;
	}
	private User getUserObj(Integer index) {
		User user = new User();
		user.setFirstName("first");
		user.setLastName("last");
		user.setDateOfBirth(new Date());
		user.setAddress("address");
		user.setPhone("+1 123-456-78" + index);
		user.setEmail("abc@xyz"+index+".com");
		user.setStatus("active");
		return user;
	}
}
