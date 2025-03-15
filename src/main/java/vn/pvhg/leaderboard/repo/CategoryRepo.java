package vn.pvhg.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.pvhg.leaderboard.model.Category;

import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
