package vn.pvhg.leaderboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.pvhg.leaderboard.dto.request.GameRequest;
import vn.pvhg.leaderboard.model.Game;
import vn.pvhg.leaderboard.service.GameService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createGame(@RequestBody GameRequest request) {
        return ResponseEntity.ok(gameService.createGame(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateGame(@RequestBody Map<String, Object> request, @PathVariable UUID id) {
        return ResponseEntity.ok().body(gameService.updateGame(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteGame(@PathVariable UUID id) {
        return gameService.deleteGame(id) ?
                ResponseEntity.ok("Delete game with id: `" + id + "`")
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<?> getGames(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return ResponseEntity.ok(gameService.getAllGames(page, size, sortBy, sortDirection));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGameById(@PathVariable UUID id) {
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @GetMapping("/category")
    public ResponseEntity<?> getGameByCategory(
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        return ResponseEntity.ok(gameService.getGamesByCategory(category, page, size, sortBy, sortDirection));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Game>> getGameByFilter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minSubmissions,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortDirection
    ) {
        return ResponseEntity.ok(gameService.getFilteredGames(name, minSubmissions, sortBy, page, size, sortDirection));
    }
}
