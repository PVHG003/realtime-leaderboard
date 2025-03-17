package vn.pvhg.leaderboard.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_type", nullable = false)
    private String categoryType;

//    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
//    private List<Game> game;
}

