package com.tjlee.oauth2.web.controller;

import com.tjlee.oauth2.dto.UserDTO;
import com.tjlee.oauth2.web.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @RequestMapping(value="/user", method = RequestMethod.GET)
    public List<UserDTO> listUser(){
        return userService.findAll();
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User create(@RequestBody User user){
        return userService.save(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id") Long id){
        userService.delete(id);
        return "success";
    }
}
