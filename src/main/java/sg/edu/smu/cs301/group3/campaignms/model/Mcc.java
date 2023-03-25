package sg.edu.smu.cs301.group3.campaignms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Mcc {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String mcc;
    private String description;
    private String category;
    private boolean isForeign;
}
