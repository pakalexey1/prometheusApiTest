package com.prometheus.tests.positiveTests;

import com.prometheus.clients.PostsClient;
import com.prometheus.dto.PostDto;
import com.prometheus.utils.JsonUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PostTests {
    private static PostsClient posts;

    @BeforeAll
    static void setup(){
        posts = new PostsClient();
    }

    @Test
    public void createPost(){
        PostDto request = JsonUtils.fromResource("data/post-create.json",PostDto.class);
        PostDto response = posts.createPost(request).then().statusCode(201).extract().as(PostDto.class);

        assertThat(response.getTitle()).isEqualTo(request.getTitle());
        assertThat(response.getUserId()).isEqualTo(request.getUserId());
        assertThat(response.getId()).isNotNull();

    }
}
