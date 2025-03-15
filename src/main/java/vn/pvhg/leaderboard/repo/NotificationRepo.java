package vn.pvhg.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.pvhg.leaderboard.model.Notification;

import java.util.UUID;

public interface NotificationRepo extends JpaRepository<Notification, UUID> {
}
