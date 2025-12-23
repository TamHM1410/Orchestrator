package application.authentication;

import application.authentication.data.AuthenticationRequest;
import application.cache.RedisKeyTypes;
import application.cache.RedisStoreRepository;
import application.cache.manager.RedisKeyManager;
import application.config.exception.CustomException;
import application.config.security.JwtTokenUtils;
import application.user.UserAccountStatus;
import application.user.UserEntity;
import application.user.UserRepository;
import application.userAuth.UserAuthService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
    private final JwtTokenUtils jwtTokenUtils;
    private final RedisStoreRepository redisStoreRepository;
    private final UserAuthService userAuthService;

    public Map<String,Object> signIn(AuthenticationRequest authenticationRequest) {
        try{
            if(authenticationRequest.getUsername()==null || authenticationRequest.getPassword()==null){
                throw  new CustomException(401,"Invalid username or password");
            }

            UserEntity userEntity=userRepository.findDistinctFirstByUsername(authenticationRequest.getUsername());

            if(userEntity==null){
                throw  new CustomException(403,"Invalid username or password");
            }

            if(userEntity.getStatus()== UserAccountStatus.INACTIVE){
                throw  new CustomException(403,"This account has been blocked");


            }

            if(!bCryptPasswordEncoder.matches(authenticationRequest.getPassword(),userEntity.getPassword())){
                throw  new CustomException(403,"Invalid password");
            }

            String token= jwtTokenUtils.generateToken(userEntity);

            Map<String,Object> returnedData = new HashMap<>();
            returnedData.put("username",userEntity.getUsername());
            returnedData.put("token",token);

            //#region store to redis
            String key=RedisKeyManager.detailKey(RedisKeyTypes.USER_DETAIL.toString(),userEntity.getId());
            redisStoreRepository.saveToRedisStore(key,returnedData);
            //#endregion

            return returnedData;

        } catch (CustomException e) {
            Thread.ofVirtual().start(()->{
                userAuthService.increaseFailLoginAttemptsWhenLoginFail(authenticationRequest.getUsername());
            });
            throw new CustomException(e.getCode(),e.getMessage());
        }

    }

    public UserEntity signUp(AuthenticationRequest authenticationRequest) {
    try{
        if(authenticationRequest.getUsername()==null || authenticationRequest.getPassword()==null){
            throw  new RuntimeException("Invalid username or password");
        }

        UserEntity userEntity=userRepository.findDistinctFirstByUsername(authenticationRequest.getUsername());
        if(userEntity!=null){
            throw  new RuntimeException("User already exists");
        }

        userEntity=new UserEntity();
        userEntity.setUsername(authenticationRequest.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(authenticationRequest.getPassword()));
        userEntity=userRepository.saveAndFlush(userEntity);

        return userEntity;
    }catch (Exception e){
        System.out.println(e.getMessage());
    }
    return  null;
    }

    public void signOut(String token) {
        if(token==null){
            throw  new RuntimeException("Invalid token");

        }

        Claims claims= jwtTokenUtils.parseToken(token);

        if(claims==null){
            throw  new RuntimeException("Invalid token");
        }

        String userDetailKey=RedisKeyManager.detailKey(RedisKeyTypes.USER_DETAIL.toString(),Long.parseLong(claims.getId()));

        redisStoreRepository.deleteFromRedisStore(userDetailKey);


    }

}
