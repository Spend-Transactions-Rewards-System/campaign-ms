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
@RequestMapping("/campaign/campaigns")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;
    private final NotificationService notificationService;

    @PostMapping()
    public ResponseEntity<String> addCampaign(@RequestBody CampaignBean campaignBean) {
        try {
            Campaign savedCampaign = campaignService.addCampaign(campaignBean);
            notificationService.addNotificationsInBulk(campaignBean.getCampaignNotificationBeanList(),
                    savedCampaign.getCampaignId());
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("error");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CampaignBean>> getCampaignByCardId(@PathVariable String id) {
        List<CampaignBean> campaignBeans = campaignService.getCampaignByCardId(Long.parseLong(id))
                .stream().map(campaign -> CampaignBean.fromCampaignModel(campaign, notificationService
                        .getNotificationsByCampaignId(campaign.getCampaignId()))).toList();
        return ResponseEntity.ok(campaignBeans);
    }

    @GetMapping()
    public ResponseEntity<List<CampaignBean>> getAllCampaign() {
        List<CampaignBean> campaigns = campaignService.getAllCampaign()
                .stream().map(campaign -> CampaignBean.fromCampaignModel(campaign, notificationService
                        .getNotificationsByCampaignId(campaign.getCampaignId()))).toList();


        return ResponseEntity.ok(campaigns);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editCampaign(@PathVariable String id, @RequestBody CampaignBean campaignBean) {
        try {
            Campaign editCampaign = campaignService.editCampaign(campaignBean, Long.parseLong(id));
            return ResponseEntity.ok(Map.of("campaign", editCampaign).toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCampaign(@PathVariable String id) {
        Campaign deletedCampaign = campaignService.deleteCampaign(Long.parseLong(id));
        notificationService.deleteNotificationsByCampaignId(Long.parseLong(id));
        return ResponseEntity.ok(Map.of("campaign", deletedCampaign).toString());
    }
}
