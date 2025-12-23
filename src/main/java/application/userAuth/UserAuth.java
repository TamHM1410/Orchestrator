package application.userAuth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="user_auth_model")
public class UserAuth {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="ip")
    private String ip;

    @Column(name="username")
    private String username;

    @Column(name="fail_login_attempts")
    private Integer failLoginAttempts=0;

    @Column(name="refresh_token")
    private String refreshToken;
}
