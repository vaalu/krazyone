/**
 * 
 */
package in.jeani.krazy.entities;

import java.util.Date;

import in.jeani.krazy.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

/**
 * 
 */
@Entity(name = "USERS")
@Data
public class UserEntity {

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "FIRSTNAME")
	private String firstName;

	@Column(name = "LASTNAME")
	private String lastName;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "PHONE")
	private String phone;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_OF_BIRTH")
	private Date dateOfBirth;

	@Column(name = "ADDRESS")
	private String address;

	public UserEntity() {
	}

	public UserEntity(User user) {
		this.id = user.getId();
		this.address = user.getAddress();
		this.dateOfBirth = user.getDateOfBirth();
		this.email = user.getEmail();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.phone = user.getPhone();
		this.status = user.getStatus();
	}

}
