package vn.pvhg.leaderboard.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Entity
@Table(name = "roles")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleType roleName;

    public String getAuthority() {
        return roleName.name();
    }

    @Getter
    @RequiredArgsConstructor
    public enum RoleType implements GrantedAuthority {
        USER, ADMIN, MODERATOR;

        @Override
        public String getAuthority() {
            return name();
        }
    }
}
