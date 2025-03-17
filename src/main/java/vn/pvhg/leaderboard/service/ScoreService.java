package vn.pvhg.leaderboard.service;

import org.springframework.stereotype.Service;
import vn.pvhg.leaderboard.dto.request.ScoreRequest;
import vn.pvhg.leaderboard.dto.response.ScoreResponse;
import vn.pvhg.leaderboard.repo.ScoreRepo;

@Service
public class ScoreService {
    private final ScoreRepo scoreRepo;

    public ScoreService(ScoreRepo scoreRepo) {
        this.scoreRepo = scoreRepo;
    }

    public ScoreResponse uploadScore(ScoreRequest request) {
//        return scoreRepo.save(request);
        return null;
    }
}
