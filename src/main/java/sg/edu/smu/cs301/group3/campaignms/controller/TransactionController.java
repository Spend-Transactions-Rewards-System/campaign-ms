package sg.edu.smu.cs301.group3.campaignms.controller;

import com.amazonaws.Response;
import com.amazonaws.http.SdkHttpMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.smu.cs301.group3.campaignms.beans.NotificationBean;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.model.CardType;
import sg.edu.smu.cs301.group3.campaignms.model.Notification;
import sg.edu.smu.cs301.group3.campaignms.service.CampaignService;
import sg.edu.smu.cs301.group3.campaignms.service.EmailService;
import sg.edu.smu.cs301.group3.campaignms.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/campaign")
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

    @GetMapping("/cardprogram")
    public ResponseEntity<List<CardType>> getAllCardProgram() {
        return ResponseEntity.ok(campaignService.getAllCardType());
    }
}
