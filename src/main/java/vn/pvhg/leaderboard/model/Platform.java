package vn.pvhg.leaderboard.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;


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
    private int id;

    @Column(name = "platform_type", nullable = false)
    private String platformType;

    @ManyToMany(mappedBy = "platforms", fetch = FetchType.LAZY)
    private List<Game> games;
}
