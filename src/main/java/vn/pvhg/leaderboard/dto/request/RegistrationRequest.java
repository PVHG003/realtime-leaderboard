package vn.pvhg.leaderboard.dto.request;

import vn.pvhg.leaderboard.model.Role;

public record RegistrationRequest(
    String username,
    String email,
    String password,
    Role.RoleType role
) {
}
