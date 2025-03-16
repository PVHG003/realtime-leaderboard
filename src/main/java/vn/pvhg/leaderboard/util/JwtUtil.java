package vn.pvhg.leaderboard.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import vn.pvhg.leaderboard.model.User;
import vn.pvhg.leaderboard.repo.UserRepo;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private static final String REFRESH_TOKEN_PREFIX = "refresh:";
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
        return generateToken(user.getId(),
                user.getRoles().stream()
                        .map(role -> role.getRoleName().getAuthority())
                        .collect(Collectors.toSet()));
    }

    public String generateToken(UUID id) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Username " + id + " not found"));
        return generateToken(user);
    }

    private String generateToken(UUID id, Set<String> authorities) {
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("leaderboard-app")
                .issuedAt(now)
                .expiresAt(now.plusMillis(jwtAccessExpirationTime))
                .subject(id.toString())
                .claim("roles", authorities)
                .claim("type", "access")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    public String generateRefreshToken(User user) {
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("http://leaderboard-app/api/auth")
                .issuedAt(now)
                .expiresAt(now.plusMillis(jwtRefreshExpirationTime))
                .subject(user.getId().toString())
                .claim("type", "refresh")
                .build();

        String refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();

        storeRefreshToken(user.getId(), refreshToken, jwtRefreshExpirationTime);

        return refreshToken;
    }

    public JwtClaimsSet decodeToken(String token) {
        try {
            Jwt decodedToken = jwtDecoder.decode(token);
            return JwtClaimsSet.builder()
                    .issuer(String.valueOf(decodedToken.getIssuer()))
                    .issuedAt(Objects.requireNonNull(decodedToken.getIssuedAt()))
                    .expiresAt(Objects.requireNonNull(decodedToken.getExpiresAt()))
                    .subject(decodedToken.getSubject())
                    .claims(claims -> claims.putAll(decodedToken.getClaims()))
                    .build();
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token", e);
        }
    }

    public void storeRefreshToken(UUID userId, String refreshToken, long expirationTime) {
        redisTemplate.opsForValue().set(REFRESH_TOKEN_PREFIX + userId, refreshToken, Duration.ofMillis(expirationTime));
    }

    public String getRefreshToken(UUID userId) {
        return (String) redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + userId);
    }

    public void deleteRefreshToken(UUID userId) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + userId);
    }

}
