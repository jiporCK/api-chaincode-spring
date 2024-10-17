package com.example.demo.dto;

public record UserDetailRequest(

    String name,

    String email,

    String sensitiveData

) {
}
