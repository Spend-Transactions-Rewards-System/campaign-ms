package sg.edu.smu.cs301.group3.campaignms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.smu.cs301.group3.campaignms.model.NotificationLogs;

public interface NotificationLogsRepository extends JpaRepository<NotificationLogs, Long> {

}
