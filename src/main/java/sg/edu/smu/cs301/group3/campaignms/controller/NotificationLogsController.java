package sg.edu.smu.cs301.group3.campaignms.controller;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.smu.cs301.group3.campaignms.model.NotificationLogs;
import sg.edu.smu.cs301.group3.campaignms.service.NotificationLogsService;

import java.util.List;

@RestController
@RequestMapping("/notificationLogs")
public class NotificationLogsController {

    private NotificationLogsService notificationLogsService;

    @Autowired
    public NotificationLogsController(NotificationLogsService notificationLogsService){
        this.notificationLogsService = notificationLogsService;
    }

    @GetMapping("/")
    public List<NotificationLogs> findAllNotificationLogs(){
        return notificationLogsService.getAllNotificationLogs();
    }

    @GetMapping("/{id}")
    public NotificationLogs findNotificationLogsById(@PathVariable Long id){
        return notificationLogsService.getNotificationLogsById(id);
    }

    @PostMapping("/")
    public ResponseEntity<?> createNotificationLogs(@RequestBody NotificationLogs notificationLogs){
        NotificationLogs object = notificationLogsService.createNotificationLogs(notificationLogs);
        return new ResponseEntity<NotificationLogs>(object, HttpStatus.CREATED);
    }
}
