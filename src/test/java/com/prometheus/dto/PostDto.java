package com.prometheus.dto;

import lombok.Data;

@Data
public class PostDto {
    private Integer id;
    private String title;
    private String body;
    private Integer userId;

    public PostDto(){}
    public PostDto(String title, String body, Integer userId){
        this.title = title;
        this.body = body;
        this.userId = userId;
    }
}
