package com.example.demo.service;

import com.example.demo.domain.UserDetails;
import com.example.demo.dto.UserDetailRequest;
import com.example.demo.mapper.UserDetailsMapper;
import com.example.demo.repo.UserDetailsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final UserDetailsRepo userDetailsRepo;
    private final UserDetailsMapper userDetailsMapper;

    public List<UserDetails> getAllUserDetails() {
        return userDetailsRepo.findAll();
    }

    public void createUserDetails(UserDetailRequest request) {
        UserDetails newUserDetails = userDetailsMapper.fromUserDetailsRequest(request);
        userDetailsRepo.save(newUserDetails);
    }
}
