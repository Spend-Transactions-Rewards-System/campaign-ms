package sg.edu.smu.cs301.group3.campaignms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/")
    public ResponseEntity<String> checkPulse() {
        System.out.println("hello");
        return ResponseEntity.ok("Campaign sm at your service");
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
    public ResponseEntity<List<Campaign>> getCampaignByCardId(@PathVariable String id) {
        List<Campaign> campaigns = campaignService.getCampaignByCardId(Integer.parseInt(id));
        return ResponseEntity.ok(campaigns);
    }

    @GetMapping("/campaign")
    public ResponseEntity<List<Campaign>> getAllCampaign() {
        List<Campaign> campaigns = campaignService.getAllCampaign();
        return ResponseEntity.ok(campaigns);
    }

    @PutMapping("/campaign/{id}")
    public ResponseEntity<String> editCampaign(@PathVariable String id, @RequestBody CampaignBean campaignBean) {
        try {
            Campaign editCampaign = campaignService.editCampaign(campaignBean, Integer.parseInt(id));
            return ResponseEntity.ok(Map.of("campaign", editCampaign).toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("error");
        }
    }

    @DeleteMapping("/campaign/{id}")
    public ResponseEntity<String> deleteCampaign(@PathVariable String id) {
        Campaign deletedCampaign = campaignService.deleteCampaign(Integer.parseInt(id));
        notificationService.deleteNotificationsByCampaignId(Integer.parseInt(id));
        return ResponseEntity.ok(Map.of("campaign", deletedCampaign).toString());
    }
}
