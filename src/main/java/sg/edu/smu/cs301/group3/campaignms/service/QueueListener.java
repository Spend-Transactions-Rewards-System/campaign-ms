package sg.edu.smu.cs301.group3.campaignms.service;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.beans.CampaignBonusAlertBean;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class QueueListener {

    Logger logger = LoggerFactory.getLogger(QueueListener.class);

    @Value("aws.card.to.campaign.queue.url")
    private String cardToCampaignQueueUrl;

    @Autowired
    SqsAsyncClient sqsAsyncClient;

    @Autowired
    EmailService emailService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    @SqsListener(value = "${aws.card.to.campaign.queue}")
    private void receiveMessage(Message<CampaignBonusAlertBean> message) {
        executorService.submit(() -> {
            try{
                // call processMessage to insert record into Aurora DB
                processMessagePayload(message.getPayload());

//                 retrieve message receipt handle and send message delete request when message is processed
                String receiptHandle = (String) message.getHeaders().get("Sqs_ReceiptHandle");
                DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                        .queueUrl(cardToCampaignQueueUrl)
                        .receiptHandle(receiptHandle)
                        .build();
                sqsAsyncClient.deleteMessage(deleteRequest);
            } catch (Exception e){
                e.printStackTrace();
                logger.error("Unable to process message" + message.getHeaders().get("Sqs_ReceiptHandle"));
            }

        });
    }

    private void processMessagePayload(CampaignBonusAlertBean payload){
        logger.info("sqs payload received: " + payload);
        emailService.sendCampaignBonusAlertToCustomer(payload);
    }
}
