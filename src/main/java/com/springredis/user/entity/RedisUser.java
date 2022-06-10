package com.springredis.user.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "user", timeToLive = 300)
public class RedisUser implements Serializable {

    @Id
    private long id;
    private String name;
    private String description;
    private String email;
    private String mobile;

    public RedisUser(long id, String name, String description, String email, String mobile) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.email = email;
        this.mobile = mobile;
    }

    public enum Permission {
        ALL,
        NONE
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
