package application.userAuth;

import application.user.UserAccountStatus;
import application.user.UserEntity;
import application.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAuthServiceImp implements UserAuthService {
    private final UserRepository userRepository;
    private UserAuthRepository userAuthRepository;

    @Override
    public void increaseFailLoginAttemptsWhenLoginFail(String username) {
        if(username==null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        UserAuth userAuth =userAuthRepository.findDistinctFirstByUsername(username);
        if(userAuth==null){
            userAuth=new UserAuth();
            userAuth.setUsername(username);
        }
        userAuth.setFailLoginAttempts(userAuth.getFailLoginAttempts()!=0? userAuth.getFailLoginAttempts() + 1 : 1);
        userAuth= userAuthRepository.save(userAuth);
        if(userAuth.getFailLoginAttempts()>10){

            UserEntity userEntity=userRepository.findDistinctFirstByUsername(username);
            if(userEntity!=null){
                userEntity.setStatus(UserAccountStatus.INACTIVE);
                userRepository.save(userEntity);
            }

        }


    }

    @Override
    public UserAuth createOrUpdateUserAuth(UserAuth userAuth) {
        return null;
    }


}
