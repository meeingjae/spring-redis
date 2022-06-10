package com.springredis.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springredis.user.entity.RedisUser;
import com.springredis.user.entity.User;
import com.springredis.user.repository.RedisUserRepository;
import com.springredis.user.repository.UserRepository;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;
    private final RedisUserRepository redisUserRepository;

    public UserController(UserRepository userRepository, RedisUserRepository redisUserRepository) {
        this.userRepository = userRepository;
        this.redisUserRepository = redisUserRepository;
    }

    @GetMapping(value = "")
    public ResponseEntity<Integer> findAllUser() {
        StopWatch redisStopWatch = new StopWatch();
        List<User> users;

        redisStopWatch.start();
        List<RedisUser> redisUsers = (List<RedisUser>) redisUserRepository.findAll();
        redisStopWatch.stop();
        logger.info("Redis - Find All : {} seconds", redisStopWatch.getTotalTimeSeconds());

        if (redisUsers.isEmpty()) {
            StopWatch dbStopWatch = new StopWatch();

            dbStopWatch.start();
            users = userRepository.findAll();
            dbStopWatch.stop();
            logger.info("DB - Find All : {} seconds", dbStopWatch.getTotalTimeSeconds());

            redisUsers.addAll(
                    users.stream()
                         .map(user -> new RedisUser(user.getId(),
                                                    user.getName(),
                                                    user.getDescription(),
                                                    user.getEmail(),
                                                    user.getMobile()))
                         .collect(Collectors.toList()));

            redisUserRepository.saveAll(redisUsers);

            return ResponseEntity.ok(users.size());
        }
        return ResponseEntity.ok(redisUsers.size());
    }

    @GetMapping(value = "findById/{id}")
    public ResponseEntity<Long> findById(@PathVariable(value = "id") long id) {
        StopWatch redisStopWatch = new StopWatch();
        StopWatch dbStopWatch = new StopWatch();

        redisStopWatch.start();
        RedisUser redisUser = redisUserRepository.findById(id).orElse(null);
        redisStopWatch.stop();
        logger.info("Redis - Find Id({}) : {} seconds", id, redisStopWatch.getTotalTimeSeconds());

        if (redisUser == null) {
            dbStopWatch.start();
            User user = userRepository.findById(id).orElse(new User());
            dbStopWatch.stop();
            logger.info("DB - Find Id({}) : {} seconds", id, dbStopWatch.getTotalTimeSeconds());
            return ResponseEntity.ok(user.getId());
        }
        return ResponseEntity.ok(redisUser.getId());
    }

    @PostMapping(value = "/{count}")
    public ResponseEntity<Long> createNumberOfUser(@PathVariable(value = "count") long count) {

        List<User> users = new ArrayList<>();
        StopWatch stopWatch = new StopWatch();

        for (int i = 0; i < count; i++) {
            String testVal = String.valueOf(i);
            User user = User.makeUser(testVal, testVal, testVal, testVal);
            users.add(user);
        }

        stopWatch.start();
        userRepository.saveAll(users);
        stopWatch.stop();
        logger.info("save takes {} seconds", stopWatch.getTotalTimeSeconds());

        return ResponseEntity.ok(count);
    }

    @DeleteMapping(value = "/all")
    public void deleteAllUsers() {
        redisUserRepository.deleteAll();
        userRepository.deleteAll();
    }
}
