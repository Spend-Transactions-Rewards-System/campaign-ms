package sg.edu.smu.cs301.group3.campaignms.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.model.Notification;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CampaignBean {
    @JsonProperty("title")
    private String title;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("end_date")
    private String endDate;
    @JsonProperty("mcc")
    private String mcc;
    @JsonProperty("min_dollar_spent")
    private double minDollarSpent;
    @JsonProperty("points_per_dollar")
    private int pointsPerDollar;
    @JsonProperty("card_program_id")
    private Long cardProgramId;
    @JsonProperty("notifications_list")
    private List<CampaignNotificationBean> campaignNotificationBeanList;

    public static CampaignBean fromCampaignModel(Campaign campaign, List<Notification> notifications) {
        return CampaignBean.builder()
                .title(campaign.getTitle())
                .startDate(campaign.getStartDate().toString())
                .endDate(campaign.getEndDate().toString())
                .mcc(campaign.getMcc())
                .minDollarSpent(campaign.getMinDollarSpent())
                .pointsPerDollar(campaign.getRewardRate())
                .cardProgramId(campaign.getCardType().getId())
                .campaignNotificationBeanList(notifications.stream()
                        .map(CampaignNotificationBean::fromNotificationModel)
                        .collect(Collectors.toList()))
                .build();
    }

}
