package vn.pvhg.leaderboard.mapper;

import org.springframework.stereotype.Component;
import vn.pvhg.leaderboard.dto.response.GameResponse;
import vn.pvhg.leaderboard.model.Category;
import vn.pvhg.leaderboard.model.Game;
import vn.pvhg.leaderboard.model.Platform;

import java.util.stream.Collectors;

@Component
public class GameMapper {

    public GameResponse objectToDto(Game game) {
        return new GameResponse(
                game.getId(),
                game.getName(),
                game.getDescription(),
                game.getCoverImageUrl(),
                game.getPlatforms().stream()
                        .map(Platform::getPlatformType)
                        .collect(Collectors.toSet()),
                game.getCategories().stream()
                        .map(Category::getCategoryType)
                        .collect(Collectors.toSet()),
                game.getCreatedAt(),
                game.getUpdatedAt()
        );
    }

    public Game dtoToObject(GameResponse gameResponse) {
        return null;
    }
}
