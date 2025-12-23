package application.cache.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

public class RedisKeyManager {

    public static  final ObjectMapper objectMapper = new ObjectMapper();

    public static String detailKey(String type, Long id) {
        return String.format("%s-Detail-I/%d", type.toUpperCase(), id);
    }

    public static String  toJsonString(Object object){
        try{
            if(object == null){
                return null;
            }
            return objectMapper.writeValueAsString(object);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
