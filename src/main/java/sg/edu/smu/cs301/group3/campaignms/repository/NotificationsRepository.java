package sg.edu.smu.cs301.group3.campaignms.repository;

import org.springframework.data.repository.CrudRepository;
import sg.edu.smu.cs301.group3.campaignms.model.Notification;

import java.util.List;

public interface NotificationsRepository extends CrudRepository<Notification, Long> {
    List<Notification> getNotificationsByCampaignId(int campaignId);
    void deleteAllByCampaignId(int campaignId);
}
