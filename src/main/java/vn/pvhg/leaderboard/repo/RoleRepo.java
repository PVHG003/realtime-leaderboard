package vn.pvhg.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.pvhg.leaderboard.model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
