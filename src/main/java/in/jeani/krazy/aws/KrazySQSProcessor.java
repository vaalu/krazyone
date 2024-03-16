/**
 * 
 */
package in.jeani.krazy.aws;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.jeani.krazy.models.User;
import software.amazon.awssdk.services.sqs.model.Message;

/**
 * 
 */
@Component
public class KrazySQSProcessor extends BaseAWSConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(KrazySQSProcessor.class);
	
	@Autowired
	ObjectMapper mapper;
	
	public void sendMessage(User user) throws Exception {
		String userSerialized = mapper.writeValueAsString(user);
		super.sendMessage(userSerialized);
	}
	
	public List<User> receiveMessage() throws Exception {
		List<Message> messages = super.receiveMessages();
		List<User> fetchedUsers = new ArrayList<>();
		User userObj = null;
		for (Message message : messages) {
			String userDetail = message.body();
			userObj = mapper.readValue(userDetail, User.class);
			fetchedUsers.add(userObj);
		}
		return fetchedUsers;
	}
	
}
