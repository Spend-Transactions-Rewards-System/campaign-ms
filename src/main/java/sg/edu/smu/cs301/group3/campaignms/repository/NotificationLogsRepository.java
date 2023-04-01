package sg.edu.smu.cs301.group3.campaignms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.edu.smu.cs301.group3.campaignms.model.NotificationLogs;
@Repository
public interface NotificationLogsRepository extends JpaRepository<NotificationLogs, Long> {

}
