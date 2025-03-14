package vn.pvhg.leaderboard.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "categories",
        indexes = {
                @Index(name = "idx_category_type", columnList = "category_type")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_game_category", columnNames = {"game_id", "category_type"})
        }
)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_type", nullable = false)
    private CategoryType categoryType;  // Stores as STRING in DB

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    public enum CategoryType {
        ANY_PERCENT, ONE_HUNDRED_PERCENT
    }
}
