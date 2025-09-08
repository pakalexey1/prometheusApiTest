package com.prometheus.tests;

import com.prometheus.clients.CommentsClient;
import com.prometheus.clients.PostsClient;
import com.prometheus.clients.UsersClient;
import com.prometheus.dto.PostDto;
import com.prometheus.utils.JsonUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestSuite {

    private static PostsClient posts;
    private static CommentsClient comments;
    private static UsersClient users;

    @BeforeAll
    static void setup(){
        posts = new PostsClient();
        comments = new CommentsClient();
        users = new UsersClient();
    }

    @DisplayName("GET #1 / posts/{id}")
    @Test
    public void getPostById(){
        Integer[] ids = JsonUtils.fromResource("data/post-ids.json", Integer[].class);
        for (int id: ids){
            PostDto post = posts.getPostById(id)
                    .then().statusCode(200)
                    .extract().as(PostDto.class);

            assertThat(post.getId()).isEqualTo(id);
            assertThat(post.getTitle()).isNotBlank();
            assertThat(post.getBody()).isNotBlank();
        }
    }
}
