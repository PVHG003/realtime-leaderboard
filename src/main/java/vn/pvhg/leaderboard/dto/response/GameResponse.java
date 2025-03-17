package vn.pvhg.leaderboard.dto.response;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record GameResponse(
        UUID id,
        String name,
        String description,
        String coverImageUrl,
        Set<String> platforms,
        Set<String> categories,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
