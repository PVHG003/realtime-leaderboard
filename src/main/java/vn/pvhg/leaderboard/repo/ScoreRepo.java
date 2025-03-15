package vn.pvhg.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.pvhg.leaderboard.model.Score;

import java.util.UUID;

@Repository
public interface ScoreRepo extends JpaRepository<Score, UUID> {
}
