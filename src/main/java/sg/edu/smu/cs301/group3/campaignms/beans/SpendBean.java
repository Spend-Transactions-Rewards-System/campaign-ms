package sg.edu.smu.cs301.group3.campaignms.beans;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import sg.edu.smu.cs301.group3.campaignms.model.CardType;

import java.sql.Date;

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

    private String transaction_date;

    private String cardType;
}
