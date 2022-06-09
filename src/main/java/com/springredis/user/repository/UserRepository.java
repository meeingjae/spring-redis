package com.springredis.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springredis.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
