package com.example.demo.controller;

import com.example.demo.domain.UserDetails;
import com.example.demo.dto.UserDetailRequest;
import com.example.demo.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-details")
public class UserDetailsController {

    private final UserDetailsService userDetailsService;

    @GetMapping
    public List<UserDetails> getAllUserDetails() {
        return userDetailsService.getAllUserDetails();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUserDetails(@RequestBody UserDetailRequest request) {
        userDetailsService.createUserDetails(request);
    }

}
