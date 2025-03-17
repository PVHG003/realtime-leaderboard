package vn.pvhg.leaderboard.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GameResponse(
        UUID id,
        String name,
        String description,
        String coverImageUrl,
        List<String> platforms,
        List<String> categories,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
