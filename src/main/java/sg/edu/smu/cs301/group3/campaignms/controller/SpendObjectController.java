package sg.edu.smu.cs301.group3.campaignms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.smu.cs301.group3.campaignms.model.Reward;
import sg.edu.smu.cs301.group3.campaignms.model.SpendObject;
import sg.edu.smu.cs301.group3.campaignms.service.SpendObjectService;

@RestController
public class SpendObjectController {

    private SpendObjectService spendObjectService;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${aws.campaign.to.card.queue.url}")
    private String endpoint;

    public void sendMessage(Reward reward){
        queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(reward).build());
    }

    @GetMapping("/convert")
    public void convertAndSend(@RequestBody SpendObject spendObject){
        Reward reward = spendObjectService.convertToReward(spendObject);
        sendMessage(reward);
    }
}
