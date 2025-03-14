package vn.pvhg.leaderboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class RealtimeLeaderboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealtimeLeaderboardApplication.class, args);
    }

}
