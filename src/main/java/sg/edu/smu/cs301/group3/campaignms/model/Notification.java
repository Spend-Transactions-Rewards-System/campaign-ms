package sg.edu.smu.cs301.group3.campaignms.model;

import lombok.Builder;
import lombok.Data;
import sg.edu.smu.cs301.group3.campaignms.beans.NotificationBean;

import jakarta.persistence.*;

@Data
@Table(name = "notification")
@Entity
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int notificationId;
    private String message;
    private String title;
    private String platform;
    private int campaignId;
}
