package com.springredis.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springredis.user.entity.RedisUser;

@Repository
public interface RedisUserRepository extends CrudRepository<RedisUser, Long> {
}
