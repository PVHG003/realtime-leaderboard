package vn.pvhg.leaderboard.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleType roleName;

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
