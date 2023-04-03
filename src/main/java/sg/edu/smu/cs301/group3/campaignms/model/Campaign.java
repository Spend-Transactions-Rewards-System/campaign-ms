package sg.edu.smu.cs301.group3.campaignms.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;


import java.sql.Date;

@Getter
@Setter
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

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date startDate;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date endDate;
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
