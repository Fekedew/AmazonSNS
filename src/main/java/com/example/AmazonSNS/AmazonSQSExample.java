package com.example.AmazonSNS;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.List;

public class AmazonSQSExample {

    void hello() {

        //Create an Amazon SQS Queue
        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        CreateQueueRequest createQueueRequest = new CreateQueueRequest("my-queue");
        String myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();

        //Send a Message to an Amazon SQS Queue
        SendMessageRequest sendMessageRequest = new SendMessageRequest(myQueueUrl, "Hello, world!");
        sqs.sendMessage(sendMessageRequest);

        //Receive Messages from an Amazon SQS Queue
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
        for (Message message : messages) {
            System.out.println("Message body: " + message.getBody());
            sqs.deleteMessage(myQueueUrl, message.getReceiptHandle());
        }

        //Create an Amazon SNS Topic
        AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();
        CreateTopicRequest createTopicRequest = new CreateTopicRequest("my-topic");
        String myTopicArn = sns.createTopic(createTopicRequest).getTopicArn();

        //Subscribe to an Amazon SNS Topic
        SubscribeRequest subscribeRequest = new SubscribeRequest(myTopicArn, "email", "softwarefeke@gmail.com");
        sns.subscribe(subscribeRequest);

        //Publish a Message to an Amazon SNS Topic
        PublishRequest publishRequest = new PublishRequest(myTopicArn, "Hello, world!");
        sns.publish(publishRequest);
    }
}
