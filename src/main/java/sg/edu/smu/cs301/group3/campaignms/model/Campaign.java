package sg.edu.smu.cs301.group3.campaignms.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Data
@Table(name = "campaigns")
@Builder
public class Campaign {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int campaignId;
    private String title;
    private String startDate;
    private String endDate;
    private String mcc;
    private double minDollarSpent;
    private double pointsPerDollar;
    private int cardProgramId;
    private double cashbackAmount;
    private boolean isActive;
}
