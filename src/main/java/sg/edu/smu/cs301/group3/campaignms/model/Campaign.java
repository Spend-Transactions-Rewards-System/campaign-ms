package sg.edu.smu.cs301.group3.campaignms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;

@Data
@Table(name = "campaign")
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long campaignId;
    private String title;
    private Timestamp startDate;
    private Timestamp endDate;
    private String mcc;
    private double minDollarSpent;
    private int rewardRate;

    @OneToOne(cascade = {CascadeType.REFRESH})
    private CardType cardType;
    private boolean isActive;
    private String customCategory;
}
