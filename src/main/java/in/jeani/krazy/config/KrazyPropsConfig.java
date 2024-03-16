/**
 * 
 */
package in.jeani.krazy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 
 */
@Configuration
public class KrazyPropsConfig {

	@Value("${aws.access.key}")
	private String awsAccessKey;
	
	@Value("${aws.secret.key}")
	private String awsSecretKey;
	
	@Value("${aws.region}")
	private String awsRegion;

	@Value("${aws.sqs.topic}")
	private String awsSqsTopic;

	/**
	 * @return the awsAccessKey
	 */
	public String getAwsAccessKey() {
		return awsAccessKey;
	}

	/**
	 * @return the awsSecretKey
	 */
	public String getAwsSecretKey() {
		return awsSecretKey;
	}

	/**
	 * @return the awsRegion
	 */
	public String getAwsRegion() {
		return awsRegion;
	}

	/**
	 * @return the awsSqsTopic
	 */
	public String getAwsSqsTopic() {
		return awsSqsTopic;
	}
}
