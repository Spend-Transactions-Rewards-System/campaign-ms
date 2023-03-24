package sg.edu.smu.cs301.group3.campaignms.beans;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RewardBean {
    private String tenant;

    private Long transaction_id;

    private Date transaction_date;

    private Long card_id;

    private String merchant;

    private int mcc;

    private String currency;

    private Long amount;

    private Long rewardAmount;

    private String remarks;
}
