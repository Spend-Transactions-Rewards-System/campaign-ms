package sg.edu.smu.cs301.group3.campaignms.beans;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampaignBonusAlertBean {
    private String email;
    private String spendDate;
    private String merchant;
    private String cardType;
    private String currency;
    private double amount;
    private double rewardAmount;
    private double balance;
    private String remarks;
}
