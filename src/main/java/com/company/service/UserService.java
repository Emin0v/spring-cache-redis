package com.company.service;

import static java.util.Objects.isNull;

import com.company.dto.payload.UserPayload;
import com.company.dto.response.UserResponse;
import com.company.entity.User;
import com.company.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RedisTemplate redisTemplate;

    // --- with RedisTemplate ---
    public UserResponse findById(String email) {
        User user = (User) redisTemplate.opsForValue().get("user-ch");
        if (isNull(user)) {
             user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("User not found by email = " + email));
            redisTemplate.opsForValue().set("user-ch", user);
        }
        return UserResponse.fromEntity(user);
    }

//    @Cacheable(value = "user-ch", key = "#email")
//    public UserResponse findById(String email) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("User not found by email = " + email));
//        return UserResponse.fromEntity(user);
//    }


    @CachePut(value = "user-сh", key = "#userPayload.email")
    public void createUser(UserPayload userPayload) {
        User user = userPayload.toEntity();
        userRepository.save(user);
    }


    @CacheEvict(value = "user-ch", key = "#email")
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found by email = " + email));
        userRepository.delete(user);
    }

}
