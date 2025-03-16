package vn.pvhg.leaderboard.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.stereotype.Service;
import vn.pvhg.leaderboard.dto.request.RegistrationRequest;
import vn.pvhg.leaderboard.dto.response.AuthResponse;
import vn.pvhg.leaderboard.model.Role;
import vn.pvhg.leaderboard.model.User;
import vn.pvhg.leaderboard.repo.RoleRepo;
import vn.pvhg.leaderboard.repo.UserRepo;
import vn.pvhg.leaderboard.util.JwtUtil;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    public AuthService(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authManager) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    public AuthResponse register(RegistrationRequest request) {
        if (userRepo.existsByUsername(request.username())) {
            throw new RuntimeException("Username is already in use");
        }

        if (userRepo.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        Role role = roleRepo.findByRoleName(
                Optional.ofNullable(request.role())
                        .orElse(Role.RoleType.USER)
        ).orElseThrow(
                () -> new RuntimeException("Specified role does not exist")
        );

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(List.of(role))
                .build();

        userRepo.save(user);

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse login(String username, String password) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        User user = userRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username " + username + " does not exist")
        );

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse refresh(String refreshToken) {
        JwtClaimsSet jwt = jwtUtil.decodeToken(refreshToken);

        if (jwt.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("Invalid refresh token");
        }

        UUID id = UUID.fromString(jwt.getSubject());
        String newAccessToken = jwtUtil.generateToken(id);
        return new AuthResponse(newAccessToken, refreshToken);
    }

    public void logout(String refreshToken) {
        JwtClaimsSet jwt = jwtUtil.decodeToken(refreshToken);
        UUID id = UUID.fromString(jwt.getSubject());

        jwtUtil.deleteRefreshToken(id);
    }
}
