package vn.pvhg.leaderboard.dto.response;

import vn.pvhg.leaderboard.model.Score;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record ScoreResponse(
        UUID id,
        LocalTime completionTime,
        String proofLink,
        Score.Status status,
        LocalDateTime submittedAt,
        LocalDateTime verifiedAt,
        UUID submitterId
) {
}
