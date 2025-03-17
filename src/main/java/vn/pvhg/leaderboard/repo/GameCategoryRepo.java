package vn.pvhg.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.pvhg.leaderboard.model.GameCategory;

public interface GameCategoryRepo extends JpaRepository<GameCategory, Long> {
}
