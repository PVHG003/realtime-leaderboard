package vn.pvhg.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.pvhg.leaderboard.model.VerificationCode;

import java.util.UUID;

public interface VerificationCodeRepo extends JpaRepository<VerificationCode, UUID> {
}
