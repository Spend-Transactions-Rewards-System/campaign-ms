package sg.edu.smu.cs301.group3.campaignms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class SpendObject {
    private Long id;

    private Long transaction_id;

    private Long card_id;

    private String merchant;

    private int mcc;

    private String currency;

    private Long amount;

    private Date transaction_date;

    private CardType cardType;

}
