package sg.edu.smu.cs301.group3.campaignms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CardType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String tenant;

    @JsonIgnore
    @OneToMany(mappedBy = "cardType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Campaign> campaignList;
}
