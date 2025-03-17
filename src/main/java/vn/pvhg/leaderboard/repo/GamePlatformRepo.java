package vn.pvhg.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.pvhg.leaderboard.model.GamePlatform;

public interface GamePlatformRepo extends JpaRepository<GamePlatform, Long> {
}
