package vn.pvhg.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.pvhg.leaderboard.model.Game;

import java.util.UUID;

public interface GameRepo extends JpaRepository<Game, UUID> {
}
