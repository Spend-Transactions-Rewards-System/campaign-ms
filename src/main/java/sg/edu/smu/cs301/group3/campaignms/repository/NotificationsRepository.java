package sg.edu.smu.cs301.group3.campaignms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.model.Notification;

import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<Notification, Long> {
    List<Notification> getNotificationsByCampaign(Campaign campaign);
    void deleteAllByCampaign(Campaign campaign);
}
