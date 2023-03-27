package sg.edu.smu.cs301.group3.campaignms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.beans.CampaignNotificationBean;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.model.Notification;
import sg.edu.smu.cs301.group3.campaignms.repository.CampaignsRepository;
import sg.edu.smu.cs301.group3.campaignms.repository.NotificationsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationsRepository notificationsRepository;
    private final CampaignsRepository campaignsRepository;

    public List<Notification> addNotificationsInBulk(List<CampaignNotificationBean> campaignNotificationBeans, Long campaignId) {

        Campaign campaign = campaignsRepository.findById(campaignId).orElseThrow(() -> new RuntimeException(campaignId + " not found"));

        return campaignNotificationBeans.stream().map(campaignNotificationBean -> {
            campaignNotificationBean.setCampaignId(campaignId.longValue());
            Notification notification = Notification.builder()
                    .title(campaignNotificationBean.getNotificationTitle())
                    .message(campaignNotificationBean.getNotificationMessage())
                    .campaign(campaign)
                    .platform("email")
                    .build();

            System.out.println(notification);
            return notificationsRepository.save(notification);
        }).collect(Collectors.toList());
    }

    public void deleteNotificationsByCampaignId(Long campaignId) {
        Campaign campaign = campaignsRepository.findById(campaignId).orElseThrow(() -> new RuntimeException(campaignId + " Not found"));
        notificationsRepository.deleteAllByCampaign(campaign);
    }

    public List<Notification> getNotificationsByCampaignId(Long campaignId) {
        Campaign campaign = campaignsRepository.findById(campaignId).orElseThrow(() -> new RuntimeException(campaignId + " Not found"));
        return notificationsRepository.getNotificationsByCampaign(campaign);
    }
}
