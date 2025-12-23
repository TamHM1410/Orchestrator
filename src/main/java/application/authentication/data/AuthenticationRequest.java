package application.authentication.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationRequest {
    //sign in
    private String username;
    private String password;

    //sign up
    private String email;


}
