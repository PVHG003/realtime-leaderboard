package vn.pvhg.leaderboard.dto.response;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
