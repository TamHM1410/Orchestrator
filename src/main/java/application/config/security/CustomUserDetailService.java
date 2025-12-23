package application.config.security;

import application.config.exception.CustomException;
import application.user.UserAccountStatus;
import application.user.UserEntity;
import application.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@AllArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity= userRepository.findDistinctFirstByUsername(username);

        if(userEntity==null){
            throw new UsernameNotFoundException(username);
        }

        if(userEntity.getStatus()== UserAccountStatus.INACTIVE){
            throw  new CustomException(401,"This user account has been block");
        }

        return new User(userEntity.getUsername(),userEntity.getPassword(),new ArrayList<>());

    }
}
