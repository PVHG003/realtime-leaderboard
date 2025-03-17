package vn.pvhg.leaderboard.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;


@Entity
@Table(name = "scores")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalTime completionTime;

    @Column(nullable = false)
    private String proofLink;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime submittedAt;

    private LocalDateTime verifiedAt;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne()
    @JoinColumn(name = "game_category_id", nullable = false)
    private GameCategory gameCategory;

    @ManyToOne()
    @JoinColumn(name = "moderator_id")
    private User moderator;

    public enum Status {
        PENDING, VERIFIED, REJECTED
    }

}
