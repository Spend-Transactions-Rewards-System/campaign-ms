package sg.edu.smu.cs301.group3.campaignms.model;

import lombok.Builder;
import lombok.Data;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Data
@Table(name = "campaign")
@Entity
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
