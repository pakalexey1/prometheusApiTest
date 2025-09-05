package com.prometheus.clients;

import com.prometheus.core.BaseClient;
import com.prometheus.core.RequestSpecs;
import com.prometheus.dto.PostDto;
import io.restassured.response.Response;

public class PostsClient extends BaseClient {

    private static final String POSTS = "/posts";

    public PostsClient(){
        super(RequestSpecs.defaultSpec());
    }

    public Response getPost(int id){
        return get(POSTS + "/" + id);
    }

    public Response listPosts(){
        return get(POSTS);}

    public Response listPostsByUser(int userId){
        return get(POSTS + "?userId=" + userId);
    }

    public Response createPost(PostDto post){
        return post(POSTS, post);
    }

    public Response updatePost(int id, PostDto post){
        return put(POSTS +"/" + id, post);
    }

    public Response deletePost(int id){
        return delete(POSTS + "/" + id);
    }


}
