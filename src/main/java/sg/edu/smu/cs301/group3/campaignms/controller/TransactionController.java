package sg.edu.smu.cs301.group3.campaignms.controller;

import com.amazonaws.http.SdkHttpMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.smu.cs301.group3.campaignms.beans.NotificationBean;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.service.CampaignService;
import sg.edu.smu.cs301.group3.campaignms.service.EmailService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    CampaignService campaignService;
    @Autowired
    EmailService emailService;

    @PostMapping("/notification")
    public ResponseEntity<String> notifyUserOfTransaction(@RequestBody NotificationBean notificationBean) {
        try {
            Campaign campaign = campaignService.getCampaignByCampaignId(notificationBean.getCampaignId());
            SdkHttpMetadata resp = emailService.send(campaign, notificationBean.getUserEmail());
            return ResponseEntity.ok(resp.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("error");
        }
    }
}
