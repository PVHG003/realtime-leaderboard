package vn.pvhg.leaderboard.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "game_categories")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
}
