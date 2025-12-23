package application.cache;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

@Repository
public class RedisStoreRepository {

    private ValueOperations valueOperations;
    private RedisTemplate redisTemplate;


    public RedisStoreRepository(@Qualifier("redisTemplate") RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.valueOperations = this.redisTemplate.opsForValue();
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
    }

    public void saveToRedisStore(String key,Object value) {
        this.valueOperations = redisTemplate.opsForValue();
        this.valueOperations.set(key, value);

    }

    public Object getFromRedisStore(String key) {
        return this.valueOperations.get(key);
    }

    public void deleteFromRedisStore(String key) {
        this.valueOperations.getAndDelete(key);
    }

    public void clearAllRedisStore() {
        this.redisTemplate.delete(redisTemplate.keys("*"));
    }




}
