package com.example.auction.controllers;

import com.example.auction.controllers.models.UserRequest;
import com.example.auction.controllers.models.UserResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable("id") String userId) {

        return null;
    }

    @PostMapping("")
    public UserResponse addUser(@RequestBody UserRequest user){

        return null;
    }
}
