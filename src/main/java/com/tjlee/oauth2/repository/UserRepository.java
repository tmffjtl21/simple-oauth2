package com.tjlee.oauth2.repository;

import com.tjlee.oauth2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userId);
}
