package vn.pvhg.leaderboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.pvhg.leaderboard.model.Category;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

    Optional<Category> findByCategoryType(String categoryType);

    List<Category> findByCategoryTypeIn(List<String> categoryTypes);
}
