package vn.pvhg.leaderboard.dto.request;

import java.util.List;

public record GameRequest(
        String name,
        String description,
        String coverImageUrl,
        List<String> platforms,
        List<String> categories
) {
}