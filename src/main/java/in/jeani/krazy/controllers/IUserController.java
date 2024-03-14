/**
 * 
 */
package in.jeani.krazy.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.jeani.krazy.models.User;

/**
 * User management rest controller methods to be implemented
 */
@RestController
@RequestMapping("/api")
public interface IUserController {

	@RequestMapping("/home")
	public String indexHome();
	
	@PostMapping("/create")
	public User addUser(@RequestBody User user);
	
	@RequestMapping("/find/by/name/first/{name}")
	public List<User> fetchUserByFirstName(@PathVariable("name") String name);
	
	@RequestMapping("/fetch/all")
	public List<User> fetchAllUsers();
	
	@RequestMapping("/find/by/name/last/{name}")
	public List<User> fetchUserByLastName(@PathVariable("name") String name);
	
	@RequestMapping("/find/by/id/{id}")
	public User fetchUserById(@PathVariable("id") Long id);
	
	@PutMapping("/update")
	public User updateUser(@RequestBody User user);
	
	@DeleteMapping("/delete/{id}")
	public Boolean deleteUser(@PathVariable("id") Long id);
	
}
