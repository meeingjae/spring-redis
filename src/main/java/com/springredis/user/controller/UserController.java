package com.springredis.user.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springredis.user.entity.User;
import com.springredis.user.repository.RedisUserRepository;
import com.springredis.user.repository.UserRepository;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserRepository userRepository;
    private final RedisUserRepository redisUserRepository;

    public UserController(UserRepository userRepository, RedisUserRepository redisUserRepository) {
        this.userRepository = userRepository;
        this.redisUserRepository = redisUserRepository;
    }

    @PostMapping(value = "/{count}")
    public ResponseEntity<Long> createNumberOfUser(@PathVariable(value = "count") long count) {

        List<User> users = new ArrayList<>();
        StopWatch stopWatch = new StopWatch();
        for(int i =0; i<count; i++) {
            String testVal = String.valueOf(i);
            User user = User.makeUser(testVal,testVal,testVal,testVal);
            users.add(user);
        }
        stopWatch.start();
        userRepository.saveAll(users);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        return ResponseEntity.ok(count);
    }


}
