package vn.pvhg.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.pvhg.leaderboard.model.Notification;

import java.util.UUID;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, UUID> {
}
