package vn.pvhg.leaderboard.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "platforms")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "platform_type", nullable = false)
    private String platformType;
}
