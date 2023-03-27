package sg.edu.smu.cs301.group3.campaignms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MccExclusion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String mcc;
    private String tenant;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    private CardType cardType;
}
