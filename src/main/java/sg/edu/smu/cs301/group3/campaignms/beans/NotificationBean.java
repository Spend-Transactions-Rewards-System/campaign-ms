package sg.edu.smu.cs301.group3.campaignms.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NotificationBean {
    @JsonProperty("campaign_id")
    private Long campaignId;
    @JsonProperty("user_email")
    private String userEmail;
    @JsonProperty("card_program_id")
    private Long cardProgramId;
}
