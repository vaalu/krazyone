/**
 * 
 */
package in.jeani.krazy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import in.jeani.krazy.models.User;
import in.jeani.krazy.services.IUserManagement;

/**
 * 
 */
@RestController
public class UserController extends BaseAPIController implements IUserController {

	@Autowired
	IUserManagement userManagementService;
	
	@Override
	public String indexHome() {
		return "Home sweet home!!!";
	}
	
	@Override
	public User addUser(User user) {
		return userManagementService.addUser(user);
	}

	@Override
	public User addUserInQueue(User user) {
		return userManagementService.addUserInQueue(user);
	}
	
	@Override
	public List<User> fetchAllUsers() {
		return userManagementService.fetchAllUsers();
	}
	
	@Override
	public List<User> fetchUserByFirstName(String name) {
		return userManagementService.fetchUsersByFirstName(name);
	}
	
	@Override
	public List<User> fetchUserByLastName(String name) {
		return userManagementService.fetchUsersByLastName(name);
	}

	@Override
	public User fetchUserById(Long id) {
		return userManagementService.fetchUserById(id);
	}

	@Override
	public User updateUser(User user) {
		return userManagementService.updateUser(user);
	}

	@Override
	public Boolean deleteUser(Long id) {
		return userManagementService.deleteUser(id);
	}

}
