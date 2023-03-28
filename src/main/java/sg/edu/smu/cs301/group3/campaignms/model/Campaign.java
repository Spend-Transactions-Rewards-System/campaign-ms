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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long campaignId;
    private String title;
    private Timestamp startDate;
    private Timestamp endDate;
    private String merchant;
    private double minDollarSpent;
    private double rewardRate;

    private boolean isForeign;

    @ManyToOne
    @JoinColumn(name = "cardType_id", referencedColumnName = "id")
    private CardType cardType;
    private boolean isActive;

    private String customCategoryName;
}
