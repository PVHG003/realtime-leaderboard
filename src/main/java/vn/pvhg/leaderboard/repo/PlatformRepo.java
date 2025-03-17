package vn.pvhg.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.pvhg.leaderboard.model.Platform;

import java.util.List;
import java.util.Optional;

public interface PlatformRepo extends JpaRepository<Platform, Integer> {
    Optional<Platform> findByPlatformType(String platformType);

    List<Platform> findByPlatformTypeIn(List<String> platformTypes);
}
