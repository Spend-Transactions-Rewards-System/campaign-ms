package sg.edu.smu.cs301.group3.campaignms.beans;
import lombok.Builder;
import lombok.Data;
import sg.edu.smu.cs301.group3.campaignms.model.CardType;

import java.util.Date;

@Data
@Builder
public class SpendBean {
    private Long id;

    private String tenant;
    
    private Long transaction_id;

    private Long card_id;

    private String merchant;

    private int mcc;

    private String currency;

    private double amount;

    private Date transaction_date;

    private CardType cardType;
}
