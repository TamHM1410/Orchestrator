package application.authentication;


import application.authentication.data.AuthenticationRequest;
import application.user.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/auth/signIn")
    public ResponseEntity<?> signIn(@RequestBody AuthenticationRequest authenticationRequest) {

        if(authenticationRequest.getUsername() == null || authenticationRequest.getPassword() == null) {

            return ResponseEntity.badRequest().build();
        }

        Object returnedData= authenticationService.signIn(authenticationRequest);

        return ResponseEntity.ok(returnedData);
    }

    @PostMapping("/auth/signUp")
    public  ResponseEntity<?> signUp(@RequestBody AuthenticationRequest authenticationRequest){
        if(authenticationRequest.getUsername()==null || authenticationRequest.getPassword()==null){
            return ResponseEntity.badRequest().build();
        }
        UserEntity userEntity = authenticationService.signUp(authenticationRequest);
        return ResponseEntity.ok(userEntity);
    }

    @PostMapping("/signOut")
    public void logout(HttpServletRequest request){

        String token = request.getHeader("Authorization");

        authenticationService.signOut(token);

    }
}
