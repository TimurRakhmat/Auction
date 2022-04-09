package com.example.auction.controllers;

import com.example.auction.controllers.models.UserRequest;
import com.example.auction.controllers.models.UserDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") String userId) {

        return null;
    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody UserRequest user){

        return null;
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody UserRequest user){

        return null;
    }

    @PostMapping("/money")
    public UserDto money(@RequestBody UserRequest user){

        return null;
    }
}
