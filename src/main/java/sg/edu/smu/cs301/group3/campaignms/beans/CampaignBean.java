package sg.edu.smu.cs301.group3.campaignms.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
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
    private int minDollarSpent;
    @JsonProperty("points_per_dollar")
    private double pointsPerDollar;
    @JsonProperty("card_program_id")
    private int cardProgramId;
    @JsonProperty("cashback_amount")
    private double cashbackAmount;
    @JsonProperty("notifications_list")
    private List<NotificationBean> notificationBeanList;


}
