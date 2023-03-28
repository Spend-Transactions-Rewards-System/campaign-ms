package sg.edu.smu.cs301.group3.campaignms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import java.util.*;

@Data
@Table(name = "custom_category")
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CustomCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private List<Mcc> mccList;

    private String merchant;

}
