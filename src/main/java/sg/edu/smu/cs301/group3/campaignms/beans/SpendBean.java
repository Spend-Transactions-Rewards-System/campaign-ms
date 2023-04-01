package sg.edu.smu.cs301.group3.campaignms.beans;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpendBean {

    private String id;

    private String tenant;
    
    private String transaction_id;

    private String card_id;

    private String merchant;

    private int mcc;

    private String currency;

    private double amount;

    private String transaction_date;

    private String cardType;
}
