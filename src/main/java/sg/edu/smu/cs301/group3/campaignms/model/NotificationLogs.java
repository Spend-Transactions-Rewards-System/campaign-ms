package sg.edu.smu.cs301.group3.campaignms.model;

import jakarta.persistence.*;
import lombok.Data;
import sg.edu.smu.cs301.group3.campaignms.constants.Status;

@Data
@Entity
public class NotificationLogs {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long notificationLogsId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String errorMessage;

    private String message;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Notification notification;
}