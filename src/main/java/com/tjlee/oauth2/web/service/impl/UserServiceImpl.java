package com.tjlee.oauth2.web.service.impl;

import com.tjlee.oauth2.domain.User;
import com.tjlee.oauth2.dto.UserDTO;
import com.tjlee.oauth2.mapper.UserMapper;
import com.tjlee.oauth2.repository.UserRepository;
import com.tjlee.oauth2.web.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userId);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public List<UserDTO> findAll() {
        return userMapper.toListDTO(userRepository.findAll());
    }

    //TODO 아직 미구현
    @Override
    public org.springframework.security.core.userdetails.User save(org.springframework.security.core.userdetails.User user) {
        return null;
    }

    //TODO 아직 미구현
    @Override
    public void delete(Long id) {
    }
}
