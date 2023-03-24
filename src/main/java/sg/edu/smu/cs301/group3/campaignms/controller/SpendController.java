package sg.edu.smu.cs301.group3.campaignms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.smu.cs301.group3.campaignms.beans.RewardBean;
import sg.edu.smu.cs301.group3.campaignms.beans.SpendBean;
import sg.edu.smu.cs301.group3.campaignms.service.SpendService;

@RestController
public class SpendController {

    private SpendService spendService;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${aws.campaign.to.card.queue.url}")
    private String campaignToCardQueueUrl;

    public void sendMessage(RewardBean reward){
        queueMessagingTemplate.send(campaignToCardQueueUrl, MessageBuilder.withPayload(reward).build());
    }

    @GetMapping("/convert")
    public void convertAndSend(@RequestBody SpendBean spendBean){
        RewardBean reward = spendService.convertToReward(spendBean);
        sendMessage(reward);
    }
}
