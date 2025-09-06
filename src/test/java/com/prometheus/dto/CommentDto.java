package com.prometheus.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Integer postId;
    private Integer id;
    private String name;
    private String email;
    private String body;
}
