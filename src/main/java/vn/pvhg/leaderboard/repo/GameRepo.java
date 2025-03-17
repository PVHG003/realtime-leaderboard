package vn.pvhg.leaderboard.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.pvhg.leaderboard.model.Game;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameRepo extends JpaRepository<Game, UUID> {
    boolean existsByNameIgnoreCase(String name);

    @Query("""
            SELECT g FROM Game g
            JOIN g.gameCategories gc
            JOIN gc.category c
            WHERE c.categoryType = :categoryType
            """)
    List<Game> findGameByCategory(@Param("categoryType") String category, Pageable pageable);
}
