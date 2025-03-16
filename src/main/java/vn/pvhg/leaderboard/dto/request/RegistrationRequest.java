package vn.pvhg.leaderboard.dto.request;

public record RegistrationRequest(
    String username,
    String email,
    String password
) {
}
