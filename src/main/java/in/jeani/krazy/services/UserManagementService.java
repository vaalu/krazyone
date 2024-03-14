/**
 * 
 */
package in.jeani.krazy.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.jeani.krazy.entities.UserEntity;
import in.jeani.krazy.models.User;
import in.jeani.krazy.repositories.UserManagementRepository;

/**
 * 
 */
@Service
public class UserManagementService implements IUserManagement {

	@Autowired
	UserManagementRepository userRepo;
	
	@Override
	public User addUser(User user) {
		return new User(userRepo.saveAndFlush(new UserEntity(user)));
	}

	@Override
	public List<User> fetchUsersByFirstName(String firstName) {
		List<UserEntity> userEntities = userRepo.findByFirstName(firstName);
		List<User> users = userEntities.stream().map(userEntity -> new User(userEntity)).toList();
		return users;
	}
	
	@Override
	public List<User> fetchUsersByLastName(String lastName) {
		List<UserEntity> userEntities = userRepo.findByLastName(lastName);
		List<User> users = userEntities.stream().map(userEntity -> new User(userEntity)).toList();
		return users;
	}

	@Override
	public User fetchUserById(Long id) {
		Optional<UserEntity> optionalUser = userRepo.findById(id);
		if (optionalUser.get() != null) {
			return new User(optionalUser.get());
		}
		return null;
	}
	
	@Override
	public List<User> fetchAllUsers() {
		List<UserEntity> userEntities = userRepo.findAll();
		List<User> users = userEntities.stream().map(userEntity -> new User(userEntity)).toList();
		return users;
	}
	
	@Override
	public List<User> fetchUsersByAttrs(User user) {
		if (null != user) {
			String firstName = user.getFirstName();
			String lastName = user.getLastName();
			Long id = user.getId();
			String email = user.getEmail();
			String phone = user.getPhone();
			Date dateOfBirth = user.getDateOfBirth();
			
		}
		return null;
	}

	@Override
	public User updateUser(User user) {
		UserEntity updatedUser = userRepo.saveAndFlush(new UserEntity(user));
		return new User(updatedUser);
	}

	@Override
	public boolean deleteUser(Long id) {
		boolean isDeleted = false;
		Optional<UserEntity> optionalUser = userRepo.findById(id);
		UserEntity userForDeletion = optionalUser.get();
		if (null != userForDeletion) {
			userRepo.delete(userForDeletion);
			isDeleted = true;
		}
		return isDeleted;
	}

}
