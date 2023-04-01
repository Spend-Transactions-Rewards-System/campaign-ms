package sg.edu.smu.cs301.group3.campaignms.beans;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class RewardBean {
    private String tenant;

    private String transactionId;

    private Date transactionDate;

    private String cardId;

    private String merchant;

    private int mcc;

    private String currency;

    private double amount;

    private double rewardAmount;

    private String remarks;
}
