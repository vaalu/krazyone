/**
 * 
 */
package in.jeani.krazy.services;

import java.util.List;

import in.jeani.krazy.models.User;

/**
 * 
 */
public interface IUserManagement {
	public User addUser(User user);
	public User addUserInQueue(User user);
	public List<User> fetchUsersByFirstName(String firstName);
	public List<User> fetchUsersByLastName(String lastName);
	public List<User> fetchUsersByAttrs(User user);
	public User fetchUserById(Long id);
	public User updateUser(User user);
	public boolean deleteUser(Long id);
	public List<User> fetchAllUsers();
}
