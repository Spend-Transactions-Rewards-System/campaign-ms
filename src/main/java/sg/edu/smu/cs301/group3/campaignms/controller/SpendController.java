package sg.edu.smu.cs301.group3.campaignms.controller;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.smu.cs301.group3.campaignms.beans.RewardBean;
import sg.edu.smu.cs301.group3.campaignms.beans.SpendBean;
import sg.edu.smu.cs301.group3.campaignms.service.SpendService;

import java.util.List;

@RestController
@RequestMapping("/campaign")
@RequiredArgsConstructor
public class SpendController {

    private final SpendService spendService;
    private final SqsTemplate queueMessagingTemplate;

    @Value("${aws.campaign.to.card.queue.url}")
    private String campaignToCardQueueUrl;

    public void sendMessage(List<RewardBean> reward){
        queueMessagingTemplate.send(campaignToCardQueueUrl, MessageBuilder.withPayload(reward).build());
    }

    @PostMapping ("/spends")
    public ResponseEntity<String> convertSpendToReward(@RequestBody SpendBean spendBean){
        List<RewardBean> reward = spendService.convertToReward(spendBean);
        sendMessage(reward);

        return ResponseEntity.ok("");
    }
}
