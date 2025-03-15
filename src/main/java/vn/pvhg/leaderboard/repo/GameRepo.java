package vn.pvhg.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.pvhg.leaderboard.model.Game;

import java.util.UUID;

@Repository
public interface GameRepo extends JpaRepository<Game, UUID> {
}
