package sg.edu.smu.cs301.group3.campaignms.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "notificationLogs")
@Entity
public class NotificationLogs {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long notificationLogsId;

    private String status;

    private String errorMessage;

    private String message;
}
