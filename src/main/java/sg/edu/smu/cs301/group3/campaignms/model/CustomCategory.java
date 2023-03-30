package sg.edu.smu.cs301.group3.campaignms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Table(name = "custom_category")
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CustomCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    @Column(unique = false)
    private List<Mcc> mccList;

    private String merchant;

}
