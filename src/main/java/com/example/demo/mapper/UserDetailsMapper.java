package com.example.demo.mapper;


import com.example.demo.domain.UserDetails;
import com.example.demo.dto.UserDetailRequest;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring")
public interface UserDetailsMapper {

    UserDetails fromUserDetailsRequest(UserDetailRequest request);

}
