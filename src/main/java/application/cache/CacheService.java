package application.cache;

import application.cache.manager.RedisKeyManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CacheService {
    public void cachedUserToken(Long userId,String token) {
        if(userId==null) {
            throw new RuntimeException("Not allowed to set user token");
        }

        String key=RedisKeyManager.detailKey(RedisKeyTypes.USER_DETAIL.toString(),userId);


    }
}
