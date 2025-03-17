package vn.pvhg.leaderboard.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.pvhg.leaderboard.dto.request.GameRequest;
import vn.pvhg.leaderboard.dto.response.GameResponse;
import vn.pvhg.leaderboard.mapper.GameMapper;
import vn.pvhg.leaderboard.model.*;
import vn.pvhg.leaderboard.repo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameService {
    private static final Logger log = LoggerFactory.getLogger(GameService.class);
    private final GameRepo gameRepo;
    private final CategoryRepo categoryRepo;
    private final PlatformRepo platformRepo;
    private final GameMapper gameMapper;
    private final GameCategoryRepo gameCategoryRepo;
    private final GamePlatformRepo gamePlatformRepo;

    public GameService(GameRepo gameRepo, CategoryRepo categoryRepo, PlatformRepo platformRepo, GameMapper gameMapper, GameCategoryRepo gameCategoryRepo, GamePlatformRepo gamePlatformRepo) {
        this.gameRepo = gameRepo;
        this.categoryRepo = categoryRepo;
        this.platformRepo = platformRepo;
        this.gameMapper = gameMapper;
        this.gameCategoryRepo = gameCategoryRepo;
        this.gamePlatformRepo = gamePlatformRepo;
    }

    public GameResponse createGame(GameRequest request) {
        if (gameRepo.existsByNameIgnoreCase(request.name())) {
            throw new RuntimeException("Game name already exists");
        }

        List<Platform> platforms = new ArrayList<>(platformRepo.findByPlatformTypeIn(request.platforms()));
        List<Category> categories = new ArrayList<>(categoryRepo.findByCategoryTypeIn(request.categories()));

        Game savedGame = gameRepo.save(Game.builder()
                .name(request.name())
                .description(request.description())
                .coverImageUrl(request.coverImageUrl())
                .gameCategories(new ArrayList<>())
                .gamePlatforms(new ArrayList<>())
                .build());

        List<GameCategory> gameCategories = categories.stream()
                .map(category -> GameCategory.builder()
                        .game(savedGame)
                        .category(category)
                        .build())
                .toList();
        gameCategoryRepo.saveAll(gameCategories);

        List<GamePlatform> gamePlatforms = platforms.stream()
                .map(platform -> GamePlatform.builder()
                        .game(savedGame)
                        .platform(platform)
                        .build())
                .toList();
        gamePlatformRepo.saveAll(gamePlatforms);

        savedGame.getGameCategories().addAll(gameCategories);
        savedGame.getGamePlatforms().addAll(gamePlatforms);

        log.debug("Creating new game");
        log.debug("Request: {}", request);
        log.debug("Created game");
        log.debug("Game: {}", savedGame.toString());
        log.debug("Platforms: {}", platforms.stream().map(Platform::getPlatformType).toList());
        log.debug("Categories: {}", categories.stream().map(Category::getCategoryType).toList());

        return gameMapper.objectToDto(gameRepo.save(savedGame));
    }

    public GameResponse updateGame(UUID id, Map<String, Object> request) {
        Game game = gameRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Game not found")
        );

        request.forEach((key, value) -> {
            switch (key) {
                case "name":
                    if (gameRepo.existsByNameIgnoreCase(value.toString())) {
                        throw new RuntimeException("Game name already exists");
                    }
                    game.setName(value.toString());
                    break;
                case "description":
                    game.setDescription(value.toString());
                    break;
                case "coverImageUrl":
                    game.setCoverImageUrl(value.toString());
                    break;
                case "platforms":
                    List<String> platformTypes = (List<String>) value;
                    List<Platform> platforms = platformRepo.findByPlatformTypeIn(platformTypes);

                    game.getGamePlatforms().clear();

                    List<GamePlatform> gamePlatforms = platforms.stream()
                            .map(platform -> GamePlatform.builder()
                                    .game(game)
                                    .platform(platform)
                                    .build())
                            .toList();
                    game.getGamePlatforms().addAll(gamePlatforms);
                    break;
                case "categories":
                    List<String> categoryTypes = (List<String>) value;
                    List<Category> categories = categoryRepo.findByCategoryTypeIn(categoryTypes);

                    game.getGameCategories().clear();

                    List<GameCategory> gameCategories = categories.stream()
                            .map(category -> GameCategory.builder()
                                    .game(game)
                                    .category(category)
                                    .build())
                            .toList();
                    game.getGameCategories().addAll(gameCategories);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        return gameMapper.objectToDto(gameRepo.save(game));
    }


    public boolean deleteGame(UUID id) {
        if (gameRepo.existsById(id)) {
            gameRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public List<GameResponse> getAllGames(int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = sort(page, size, sortBy, sortDirection);

        Page<Game> games = gameRepo.findAll(pageable);

        return games.stream()
                .map(gameMapper::objectToDto)
                .collect(Collectors.toList());
    }

    public GameResponse getGameById(UUID id) {
        return gameMapper.objectToDto(gameRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Game not found")
        ));
    }

    public List<GameResponse> getGamesByCategory(String category, int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = sort(page, size, sortBy, sortDirection);

        List<Game> games = gameRepo.findGameByCategory(category, pageable);
        return games.stream()
                .map(gameMapper::objectToDto)
                .toList();
    }

    public List<Game> getFilteredGames(String name, Integer minSubmissions, String sortBy, int page, int size, String sortDirection) {
        return null;
    }

    private Pageable sort(int page, int size, String sortBy, String sortDirection) {
        Sort.Direction direction = (sortDirection != null && sortDirection.equalsIgnoreCase("desc"))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Sort sort = (sortBy != null && !sortBy.isEmpty()) ? Sort.by(direction, sortBy) : Sort.by(direction, "name");
        return PageRequest.of(page, size, sort);
    }
}
