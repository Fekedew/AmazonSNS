package com.example.AmazonSNS;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.SubscribeResult;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmazonSnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmazonSnsApplication.class, args);

		//Create an Amazon SNS Topic
		AmazonSNS sns = AmazonSNSClientBuilder.standard()
				.withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
				.build();
		String topicArn = "arn:aws:sns:us-east-1:035145395470:Test123";

		String message = "Hello, AWS SNS from Java!";

		// Publish the message to the topic
		PublishRequest publishRequest = new PublishRequest(topicArn, message);
		PublishResult publishResult = sns.publish(publishRequest);

		// Print the message ID if successful
		System.out.println("Message sent with MessageId: " + publishResult.getMessageId());

		// Specify the protocol and endpoint for the subscription
		String protocol = "email"; // You can use other protocols like "sms", "http", etc.
		String endpoint = "softwarefeke@gmail.com"; // Change to your desired endpoint

		// Subscribe to the topic
		SubscribeRequest subscribeRequest = new SubscribeRequest(topicArn, protocol, endpoint);
		SubscribeResult subscribeResult = sns.subscribe(subscribeRequest);

		// Print the subscription ARN if successful
		System.out.println("Subscription ARN: " + subscribeResult.getSubscriptionArn());

	}
}
