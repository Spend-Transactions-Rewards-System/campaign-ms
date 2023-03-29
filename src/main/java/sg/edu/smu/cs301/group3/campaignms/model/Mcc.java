package sg.edu.smu.cs301.group3.campaignms.model;

import jakarta.persistence.Entity;
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
    private Integer mcc;
    private String description;
    private String mccGroup;
}
