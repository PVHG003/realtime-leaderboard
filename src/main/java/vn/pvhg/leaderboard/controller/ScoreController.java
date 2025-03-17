package vn.pvhg.leaderboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.pvhg.leaderboard.service.ScoreService;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }


    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> uploadScore(@RequestBody String scoreRequest) {
        // TODO: Process and save score
        return ResponseEntity.ok("Score submission received: " + scoreRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScore(@PathVariable int id) {
        return ResponseEntity.ok("Score deleted received: " + id);
    }

    @GetMapping
    public ResponseEntity<?> getScores(
            @RequestParam(required = false) String game,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "completionTime") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return ResponseEntity.ok("Fetching scores for game: " + game + ", category: " + category + ", user: " + user);
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<?> getLeaderboard(
            @RequestParam String game,
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "completionTime") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
//        TODO: FETCH FROM REDIS
        return ResponseEntity.ok("Fetching leaderboard for game: " + game + ", category: " + category);
    }

    @PostMapping("/{id}/verify")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<?> verifyScore(
            @PathVariable Long id,
            @RequestBody String verificationRequest
    ) {
        return ResponseEntity.ok("Score verification updated for ID: " + id + " with data: " + verificationRequest);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserScores(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "completionTime") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return ResponseEntity.ok("Fetching scores for user: " + userId);
    }
}
