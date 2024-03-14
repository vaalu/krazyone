/**
 * 
 */
package in.jeani.krazy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.jeani.krazy.entities.UserEntity;

/**
 * 
 */
@Repository
public interface UserManagementRepository extends JpaRepository<UserEntity, Long>{
	
	List<UserEntity> findByFirstName(String name);
	List<UserEntity> findByLastName(String name);
}
