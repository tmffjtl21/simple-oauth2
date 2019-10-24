package com.tjlee.oauth2.web.service;

import com.tjlee.oauth2.dto.UserDTO;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();

    User save(User user);

    void delete(Long id);
}
