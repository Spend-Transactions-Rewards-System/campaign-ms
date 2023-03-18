package sg.edu.smu.cs301.group3.campaignms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.smu.cs301.group3.campaignms.model.Notification;

import java.util.List;

public interface NotificationsRepository extends JpaRepository<Notification, Long> {
    List<Notification> getNotificationsByCampaignId(int campaignId);
    void deleteAllByCampaignId(int campaignId);
}
