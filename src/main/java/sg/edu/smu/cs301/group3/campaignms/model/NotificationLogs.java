package sg.edu.smu.cs301.group3.campaignms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.smu.cs301.group3.campaignms.constants.Status;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationLogs {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
