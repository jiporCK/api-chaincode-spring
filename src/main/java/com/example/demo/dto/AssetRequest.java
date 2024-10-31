package com.example.demo.dto;

public record AssetRequest(

        String color,

        Integer size,

        String owner,

        Integer appraisedValue

) {
}
