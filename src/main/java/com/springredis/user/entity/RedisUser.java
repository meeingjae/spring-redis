package com.springredis.user.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "user", timeToLive = 300)
public class RedisUser implements Serializable {

    @Id
    private long id;
    private String name;
    private Permission permission;

    public RedisUser(long id, String name, Permission permission) {
        this.id = id;
        this.name = name;
        this.permission = permission;
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
