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
import sg.edu.smu.cs301.group3.campaignms.model.Notification;
import sg.edu.smu.cs301.group3.campaignms.service.CampaignService;
import sg.edu.smu.cs301.group3.campaignms.service.EmailService;
import sg.edu.smu.cs301.group3.campaignms.service.NotificationService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    CampaignService campaignService;
    @Autowired
    EmailService emailService;

    @Autowired
    NotificationService notificationService;

    @PostMapping("/notification")
    public ResponseEntity<String> notifyUserOfTransaction(@RequestBody NotificationBean notificationBean) {
        try {
            Notification notification = notificationService
                    .getNotificationsByCampaignId(notificationBean.getCampaignId()).get(0);
            SdkHttpMetadata resp = emailService.send(notification, notificationBean.getUserEmail());
            return ResponseEntity.ok(resp.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("error");
        }
    }
}
