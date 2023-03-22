package sg.edu.smu.cs301.group3.campaignms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import sg.edu.smu.cs301.group3.campaignms.beans.CampaignBean;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.service.CampaignService;
import sg.edu.smu.cs301.group3.campaignms.service.NotificationService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;
    private final NotificationService notificationService;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${aws.campaign.to.card.queue.url}")
    private String endpoint;

    @GetMapping("/send/{message}")
    public void sendMessage(@PathVariable String message){
        queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(message).build());
    }

    @GetMapping("/")
    public ResponseEntity<String> checkPulse() {
        return ResponseEntity.ok("Campaign-Rewards ms at your service");
    }
    @PostMapping("/campaign")
    public ResponseEntity<String> addCampaign(@RequestBody CampaignBean campaignBean) {
        try {
            Campaign savedCampaign = campaignService.addCampaign(campaignBean);
            notificationService.addNotificationsInBulk(campaignBean.getNotificationBeanList(),
                    savedCampaign.getCampaignId());
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("error");
        }
    }

    @GetMapping("/campaign/{id}")
    public ResponseEntity<List<CampaignBean>> getCampaignByCardId(@PathVariable String id) {
        List<CampaignBean> campaignBeans = campaignService.getCampaignByCardId(Long.parseLong(id))
                .stream().map(campaign -> CampaignBean.fromCampaignModel(campaign, notificationService
                        .getNotificationsByCampaignId(campaign.getCampaignId()))).toList();
        return ResponseEntity.ok(campaignBeans);
    }

    @GetMapping("/campaign")
    public ResponseEntity<List<CampaignBean>> getAllCampaign() {
        List<CampaignBean> campaigns = campaignService.getAllCampaign()
                .stream().map(campaign -> CampaignBean.fromCampaignModel(campaign, notificationService
                        .getNotificationsByCampaignId(campaign.getCampaignId()))).toList();
        return ResponseEntity.ok(campaigns);
    }

    @PutMapping("/campaign/{id}")
    public ResponseEntity<String> editCampaign(@PathVariable String id, @RequestBody CampaignBean campaignBean) {
        try {
            Campaign editCampaign = campaignService.editCampaign(campaignBean, Long.parseLong(id));
            return ResponseEntity.ok(Map.of("campaign", editCampaign).toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("error");
        }
    }

    @DeleteMapping("/campaign/{id}")
    public ResponseEntity<String> deleteCampaign(@PathVariable String id) {
        Campaign deletedCampaign = campaignService.deleteCampaign(Long.parseLong(id));
        notificationService.deleteNotificationsByCampaignId(Long.parseLong(id));
        return ResponseEntity.ok(Map.of("campaign", deletedCampaign).toString());
    }
}
