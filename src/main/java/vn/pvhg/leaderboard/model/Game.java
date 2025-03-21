package vn.pvhg.leaderboard.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "games")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    private String coverImageUrl;

    //    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "game_platforms",
//            joinColumns = @JoinColumn(name = "game_id"),
//            inverseJoinColumns = @JoinColumn(name = "platform_id")
//    )
//    private List<Platform> platforms;

    //    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "game_categories",
//            joinColumns = @JoinColumn(name = "game_id"),
//            inverseJoinColumns = @JoinColumn(name = "category_id")
//    )
//    private List<Category> categories;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GameCategory> gameCategories = new ArrayList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GamePlatform> gamePlatforms = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", coverImageUrl='" + coverImageUrl + '\'' +
                ", gameCategories=" + gameCategories +
                ", gamePlatforms=" + gamePlatforms +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
