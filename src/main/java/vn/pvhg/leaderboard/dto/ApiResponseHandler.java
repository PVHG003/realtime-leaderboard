package vn.pvhg.leaderboard.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiResponseHandler(
        int status,
        String message,
        String timestamp,
        String path
) {
    public ApiResponseHandler(HttpStatus status, String message, String path) {
        this(status.value(), message, LocalDateTime.now().toString(), path);
    }
}
