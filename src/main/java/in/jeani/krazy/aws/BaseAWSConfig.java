/**
 * 
 */
package in.jeani.krazy.aws;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.jeani.krazy.config.KrazyPropsConfig;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import software.amazon.awssdk.services.sqs.model.SqsException;

/**
 * 
 */
@Component
public class BaseAWSConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseAWSConfig.class);
	
	@Autowired
	KrazyPropsConfig props;
	
	private Region getRegion() {
		
		String awsRegion = props.getAwsRegion();
		return Region.of(awsRegion);
		
	}
	
	private SqsClient getSqsClient() {
		SqsClient sqsClient = SqsClient
								.builder()
								.region(getRegion())
								.build();
		return sqsClient;
	}
	
	protected void sendMessage(String qMessage) {
		
		SqsClient client = getSqsClient();
		String qName = props.getAwsSqsTopic();
		
		try {
			CreateQueueRequest req = CreateQueueRequest.builder()
					.queueName(qName)
					.build();
			client.createQueue(req);
			GetQueueUrlRequest getQReq = GetQueueUrlRequest.builder().queueName(qName).build();
			String queueUrl = client.getQueueUrl(getQReq).queueUrl();
			SendMessageRequest msgReq = SendMessageRequest.builder().queueUrl(queueUrl).messageBody(qMessage).delaySeconds(5).build();
			SendMessageResponse response = client.sendMessage(msgReq);
			LOGGER.debug("Send SQS message response: ", response);
		} catch (SqsException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
	}
	
	protected List<Message> receiveMessages() {
		SqsClient client = getSqsClient();
		String qName = props.getAwsSqsTopic();
		List<Message> queueMessages = null;
		try {
			GetQueueUrlRequest getQReq = GetQueueUrlRequest.builder().queueName(qName).build();
			String queueUrl = client.getQueueUrl(getQReq).queueUrl();
			
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .maxNumberOfMessages(5)
                    .build();
            queueMessages = client.receiveMessage(receiveMessageRequest).messages();

        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
		return queueMessages;
	}
	
}
