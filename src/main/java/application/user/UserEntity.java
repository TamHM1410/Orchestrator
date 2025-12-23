package application.user;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name="user_model")
public class UserEntity implements Serializable {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private UserAccountStatus status=UserAccountStatus.ACTIVE;


}
