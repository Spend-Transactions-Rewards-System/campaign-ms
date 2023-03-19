package sg.edu.smu.cs301.group3.campaignms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "notification")
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long notificationId;
    private String message;
    private String title;
    private String platform;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Campaign campaign;
}
