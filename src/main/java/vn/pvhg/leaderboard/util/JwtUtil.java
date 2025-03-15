package vn.pvhg.leaderboard.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import vn.pvhg.leaderboard.model.User;
import vn.pvhg.leaderboard.repo.UserRepo;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private static final String REFRESH_TOKEN_KEY = "valid";

    private final long jwtAccessExpirationTime;
    private final long jwtRefreshExpirationTime;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final RedisTemplate<Object, Object> redisTemplate;
    private final UserRepo userRepo;

    public JwtUtil(
            @Value("${jwt.access-expiry}") long jwtAccessExpirationTime,
            @Value("${jwt.refresh-expiry}") long jwtRefreshExpirationTime,
            JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, RedisTemplate<Object, Object> redisTemplate, UserRepo userRepo
    ) {
        this.jwtAccessExpirationTime = jwtAccessExpirationTime;
        this.jwtRefreshExpirationTime = jwtRefreshExpirationTime;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.redisTemplate = redisTemplate;
        this.userRepo = userRepo;
    }

    public String generateToken(User user) {
        return generateToken(user.getUsername(), user.getRoles().stream()
                .map(role -> role.getRoleName().getAuthority())
                .collect(Collectors.toSet()));
    }

    public String generateToken(String username) {
        User user = userRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username " + username + " not found"));
        return generateToken(user);
    }

    private String generateToken(String username, Set<String> authorities) {
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("leaderboard-app")
                .issuedAt(now)
                .expiresAt(now.plusMillis(jwtAccessExpirationTime))
                .subject(username)
                .claim("roles", authorities)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    public String generateRefreshToken(User user) {
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("leaderboard-app")
                .issuedAt(now)
                .expiresAt(now.plusMillis(jwtRefreshExpirationTime))
                .subject(user.getUsername())
                .build();

        String refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();

        redisTemplate.opsForValue().set(refreshToken, REFRESH_TOKEN_KEY, jwtRefreshExpirationTime, TimeUnit.MILLISECONDS);

        return refreshToken;
    }

    public JwtClaimsSet decodeToken(String token) {
        try {
            Jwt decodedToken = jwtDecoder.decode(token);
            return JwtClaimsSet.builder()
                    .issuer(decodedToken.getIssuer().toString())
                    .issuedAt(Objects.requireNonNull(decodedToken.getIssuedAt()))
                    .expiresAt(Objects.requireNonNull(decodedToken.getExpiresAt()))
                    .subject(decodedToken.getSubject())
                    .claims(claims -> claims.putAll(decodedToken.getClaims()))
                    .build();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid token", e);
        }
    }

    public String refreshAccessToken(String refreshToken) {
        try {
            if (redisTemplate.opsForValue().get(refreshToken) == null) {
                throw new JwtException("Refresh token is invalid or expired");
            }

            JwtClaimsSet refreshTokenClaims = decodeToken(refreshToken);
            String username = refreshTokenClaims.getSubject();

            if (refreshTokenClaims.getExpiresAt().isBefore(Instant.now())) {
                throw new JwtException("Refresh token has expired");
            }

            redisTemplate.delete(refreshToken);

            return generateToken(username);
        } catch (JwtException e) {
            throw new RuntimeException("Invalid refresh token", e);
        }
    }
}
