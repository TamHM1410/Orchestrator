package application.userAuth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAuthReqDTO {
//        private String username;
//        private String password;
//        private String email;
}
