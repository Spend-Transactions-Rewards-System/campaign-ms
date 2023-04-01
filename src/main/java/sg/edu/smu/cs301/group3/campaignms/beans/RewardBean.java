package sg.edu.smu.cs301.group3.campaignms.beans;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class RewardBean {
    private String tenant;

    private String transaction_id;

    private Date transaction_date;

    private String card_id;

    private String merchant;

    private int mcc;

    private String currency;

    private double amount;

    private double rewardAmount;

    private String remarks;
}
