package sg.edu.smu.cs301.group3.campaignms.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import sg.edu.smu.cs301.group3.campaignms.beans.NotificationBean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Data
@Table(name = "notification")
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
