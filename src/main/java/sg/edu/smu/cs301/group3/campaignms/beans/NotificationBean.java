package sg.edu.smu.cs301.group3.campaignms.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NotificationBean {
    @JsonProperty("notification_title")
    private String notificationTitle;

    @JsonProperty("notification_message")
    private String notificationMessage;

    @JsonProperty("campaign_id")
    private int campaignId;
}
