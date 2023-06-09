package sg.edu.smu.cs301.group3.campaignms.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import sg.edu.smu.cs301.group3.campaignms.model.Notification;

@Data
@Builder
public class CampaignNotificationBean {
    @JsonProperty("notification_title")
    private String notificationTitle;

    @JsonProperty("notification_message")
    private String notificationMessage;

    @JsonProperty("campaign_id")
    private Long campaignId;

    public static CampaignNotificationBean fromNotificationModel(Notification notification) {
        return CampaignNotificationBean.builder()
                .notificationTitle(notification.getTitle())
                .notificationMessage(notification.getMessage())
                .campaignId(notification.getCampaign().getCampaignId())
                .build();
    }
}
