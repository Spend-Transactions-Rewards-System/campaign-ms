package sg.edu.smu.cs301.group3.campaignms.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Table(name = "campaign")
@Builder
public class Campaign {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int campaignId;
    private String title;
    private Timestamp startDate;
    private Timestamp endDate;
    private String mcc;
    private double minDollarSpent;
    private int rewardRate;
    private int cardProgramId;
    private boolean isActive;
    private String customCategory;
}
