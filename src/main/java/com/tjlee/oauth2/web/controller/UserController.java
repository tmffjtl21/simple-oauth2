package com.tjlee.oauth2.web.controller;

import com.tjlee.oauth2.dto.UserDTO;
import com.tjlee.oauth2.web.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/callback")
    public String login(Model model, Principal principal, @RequestParam(required = false) String code, HttpServletRequest request){
        System.out.println(request.getRemoteAddr());
        model.addAttribute("name", Optional.ofNullable(principal).map(Principal::getName).orElse(null));
        model.addAttribute("code", code);
        return "oauth/login";
    }
}
