package com.prometheus.tests;

import com.prometheus.clients.CommentsClient;
import com.prometheus.clients.PostsClient;
import com.prometheus.clients.UsersClient;
import com.prometheus.dto.CommentDto;
import com.prometheus.dto.PostDto;
import com.prometheus.dto.UserDto;
import com.prometheus.utils.JsonUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


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

    //  3 GET tests
    @DisplayName("GET #1 /posts/{id}")
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
    
    @DisplayName("GET #2 /posts{id}/comments")
    @Test
    public void getCommentsForPostThatHasFiveComments(){
        Integer[] ids = JsonUtils.fromResource("data/post-ids.json", Integer[].class);
        for (int postId: ids){
            List<CommentDto> list = comments.listCommentsForPost(postId)
                    .then().statusCode(200)
                    .extract().jsonPath().getList("",CommentDto.class);

            assertThat(list).hasSize(5);
            for (CommentDto c : list) {
                assertThat(c.getPostId()).isEqualTo(postId);
            }
        }
    }

    @DisplayName("GET-3 (NEGATIVE): /comments?postId=9999")
    @Test
    public void getCommentsWithInvalidId() {
        List<CommentDto> list = comments.listCommentsForPost(9999)
                .then().statusCode(200)
                .extract().jsonPath().getList("", CommentDto.class);

        assertThat(list).isEmpty();
    }

    // 3 POST tests

    @DisplayName("POST #1: create /posts using external JSON")
    @Test
    public void createPostFromJson() {
        PostDto request = JsonUtils.fromResource("data/post-create.json", PostDto.class);

        PostDto created = posts.createPost(request)
                .then().statusCode(201)
                .extract().as(PostDto.class);

        assertThat(created.getTitle()).isEqualTo(request.getTitle());
        assertThat(created.getBody()).isEqualTo(request.getBody());
        assertThat(created.getUserId()).isEqualTo(request.getUserId());
        assertThat(created.getId()).isNotNull();
    }

    @DisplayName("POST #2: create /comments using external JSON")
    @Test
    void createCommentFromJson() {
        CommentDto request = JsonUtils.fromResource("data/comment-create.json", CommentDto.class);

        CommentDto created = comments.createComment(request)
                .then().statusCode(201)
                .extract().as(CommentDto.class);

        assertThat(created.getPostId()).isEqualTo(request.getPostId());
        assertThat(created.getName()).isEqualTo(request.getName());
        assertThat(created.getEmail()).isEqualTo(request.getEmail());
        assertThat(created.getBody()).isEqualTo(request.getBody());
        assertThat(created.getId()).isNotNull();
    }

    @DisplayName("POST-3 POST new post, GET id yields {}")
    @Test
    void post_non_persistent_check() {
        PostDto req = JsonUtils.fromResource("data/post-create.json", PostDto.class);

        PostDto created = posts.createPost(req)
                .then().statusCode(201)
                .extract().as(PostDto.class);

        String getPayload = posts.getPostById(created.getId())
                .then().statusCode(404)
                .extract().asString().trim();

        // JSONPlaceholder fakes creation; GET won't find it
        assertThat(getPayload).isIn("{}", "");
    }

    // 3 PUT tests
    @DisplayName("PUT #1: update /posts/{id} using external JSON")
    @Test
    void updatePostFromJson() {
        PostDto request = JsonUtils.fromResource("data/post-update.json", PostDto.class);

        PostDto updated = posts.updatePost(request.getId(), request)
                .then().statusCode(200)
                .extract().as(PostDto.class);

        assertThat(updated.getId()).isEqualTo(request.getId());
        assertThat(updated.getTitle()).isEqualTo(request.getTitle());
        assertThat(updated.getBody()).isEqualTo(request.getBody());
        assertThat(updated.getUserId()).isEqualTo(request.getUserId());
    }

    @DisplayName("PUT #2: update /users/{id} using external JSON")
    @Test
    void updateUserFromJson() {
        UserDto request = JsonUtils.fromResource("data/user-update.json", UserDto.class); // contains id=1

        UserDto updated = users.updateUser(request.getId(), request)
                .then().statusCode(200)
                .extract().as(UserDto.class);

        assertThat(updated.getId()).isEqualTo(request.getId());
        assertThat(updated.getName()).isEqualTo(request.getName());
        assertThat(updated.getCompany().getName()).isEqualTo(request.getCompany().getName());
        assertThat(updated.getAddress().getCity()).isEqualTo(request.getAddress().getCity());
    }

    @DisplayName("PUT #3: GET before/after PUT still returns original")
    @Test
    void put_non_persistent_check() {
        PostDto before = posts.getPostById(1)
                .then().statusCode(200)
                .extract().as(PostDto.class);

        PostDto change = new PostDto();
        change.setId(1);
        change.setUserId(before.getUserId());
        change.setTitle("UPDATED" + System.currentTimeMillis());
        change.setBody(before.getBody());

        posts.updatePost(1, change).then().statusCode(200);

        PostDto after = posts.getPostById(1)
                .then().statusCode(200)
                .extract().as(PostDto.class);

        assertThat(after.getTitle()).isEqualTo(before.getTitle());
    }

    // 3 DELETE tests
    @DisplayName("DELETE #1: delete /posts/{id}")
    @Test
    void delete_post() {
        String payload = posts.deletePost(1)
                .then().statusCode(200)
                .extract().asString().trim();

        assertThat(payload).isIn("{}", "");
    }

    @DisplayName("DELETE #2: delete /comments/{id}")
    @Test
    void delete_comment() {
        String payload = comments.deleteComment(1)
                .then().statusCode(200)
                .extract().asString().trim();

        assertThat(payload).isIn("{}", "");
    }

    @DisplayName("DELETE #3: delete non-existing user id")
    @Test
    void deleteNonExistingUser() {
        String payload = users.deleteUser(9999)
                .then().statusCode(200)
                .extract().asString().trim();

        assertThat(payload).isIn("{}", "");
    }

}
