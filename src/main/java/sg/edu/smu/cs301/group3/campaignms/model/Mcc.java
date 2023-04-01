package sg.edu.smu.cs301.group3.campaignms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Mcc {
    @Id
    private Integer mcc;
    private String description;
    private String mccGroup;

    @OneToMany(mappedBy = "mcc")
    private List<CustomCategory> customCategory;
}
