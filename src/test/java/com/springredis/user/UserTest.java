package com.springredis.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.springredis.user.entity.RedisUser;
import com.springredis.user.repository.RedisUserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserTest {

    @Autowired
    private RedisUserRepository repository;

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }

    final static String NAME = "test";

    @Test
    void redis_user_test() {
        RedisUser redisUser = new RedisUser(1, NAME, "des", "email", "mobile");

        repository.save(redisUser);

        RedisUser findUser = repository.findById(redisUser.getId()).get();

        assertThat(findUser.getName())
                .isEqualTo(NAME);
    }
}
