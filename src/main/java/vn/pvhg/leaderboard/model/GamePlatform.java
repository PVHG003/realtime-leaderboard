package vn.pvhg.leaderboard.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "game_platforms")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GamePlatform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "platform_id", nullable = false)
    private Platform platform;
}
