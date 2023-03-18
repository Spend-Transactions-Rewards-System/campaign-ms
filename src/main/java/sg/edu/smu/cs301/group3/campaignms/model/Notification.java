package sg.edu.smu.cs301.group3.campaignms.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import sg.edu.smu.cs301.group3.campaignms.beans.NotificationBean;

@Data
@Table(name = "notification")
@Builder
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long notificationId;
    private String message;
    private String title;
    private String platform;
    private Long campaignId;
}
