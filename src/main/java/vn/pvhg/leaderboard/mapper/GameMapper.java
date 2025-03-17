package vn.pvhg.leaderboard.mapper;

import org.springframework.stereotype.Component;
import vn.pvhg.leaderboard.dto.response.GameResponse;
import vn.pvhg.leaderboard.model.Game;

import java.util.List;

@Component
public class GameMapper {
    public GameResponse objectToDto(Game game) {
        return new GameResponse(
                game.getId(),
                game.getName(),
                game.getDescription(),
                game.getCoverImageUrl(),
                game.getGamePlatforms() != null ?
                        game.getGamePlatforms().stream()
                                .map(gamePlatform -> gamePlatform.getPlatform().getPlatformType())
                                .toList()
                        : List.of(),
                game.getGameCategories() != null ?
                        game.getGameCategories().stream()
                                .map(gameCategory -> gameCategory.getCategory().getCategoryType())
                                .toList()
                        : List.of(),
                game.getCreatedAt(),
                game.getUpdatedAt()
        );
    }

    public Game dtoToObject(GameResponse gameResponse) {
        return Game.builder()
                .name(gameResponse.name())
                .description(gameResponse.description())
                .coverImageUrl(gameResponse.coverImageUrl())
                .build();
    }
}
