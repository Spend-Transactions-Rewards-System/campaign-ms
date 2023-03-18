package sg.edu.smu.cs301.group3.campaignms.service;

import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.beans.NotificationBean;
import sg.edu.smu.cs301.group3.campaignms.model.Notification;
import sg.edu.smu.cs301.group3.campaignms.repository.NotificationsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final NotificationsRepository notificationsRepository;

    public NotificationService(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    public List<Notification> addNotificationsInBulk(List<NotificationBean> notificationBeans, Long campaignId) {
        return notificationBeans.stream().map(notificationBean -> {
            notificationBean.setCampaignId(campaignId.intValue());
            Notification notification = Notification.builder()
                    .title(notificationBean.getNotificationTitle())
                    .message(notificationBean.getNotificationMessage())
                    .campaignId(notificationBean.getCampaignId())
                    .platform("email")
                    .build();
            System.out.println(notification);
            return notificationsRepository.save(notification);
        }).collect(Collectors.toList());
    }

    public void deleteNotificationsByCampaignId(int campaignId) {
        notificationsRepository.deleteAllByCampaignId(campaignId);
    }

    public List<Notification> getNotificationsByCampaignId(int campaignId) {
        return notificationsRepository.getNotificationsByCampaignId(campaignId);
    }
}
