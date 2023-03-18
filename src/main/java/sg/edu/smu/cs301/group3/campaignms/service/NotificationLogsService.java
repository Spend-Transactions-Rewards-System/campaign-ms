package sg.edu.smu.cs301.group3.campaignms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.beans.CampaignBean;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.model.NotificationLogs;
import sg.edu.smu.cs301.group3.campaignms.repository.CampaignsRepository;
import sg.edu.smu.cs301.group3.campaignms.repository.NotificationLogsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationLogsService {
    private NotificationLogsRepository notificationLogsRepository;

    @Autowired
    public NotificationLogsService(NotificationLogsRepository notificationLogsRepository){
        this.notificationLogsRepository = notificationLogsRepository;
    }

    public List<NotificationLogs> getAllNotificationLogs(){
        return notificationLogsRepository.findAll();
    }

    public NotificationLogs getNotificationLogsById(Long id){
        Optional<NotificationLogs> notificationLogs = notificationLogsRepository.findById(id);
        if(!notificationLogs.isPresent()){
            return null;
        }

        return notificationLogs.get();
    }

    public NotificationLogs createNotificationLogs(NotificationLogs notificationLogs){
        return notificationLogsRepository.save(notificationLogs);
    }
}
