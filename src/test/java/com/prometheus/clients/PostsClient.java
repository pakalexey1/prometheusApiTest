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

    // GET a post by ID
    public Response getPostById(int id){
        return get(POSTS + "/" + id);
    }
    // GET all posts
    public Response listPosts(){
        return get(POSTS);
    }

    //GET posts by userID
    public Response listPostsByUser(int userId){
        return get(POSTS + "?userId=" + userId);
    }

    //CREATE / POST a post
    public Response createPost(PostDto post){
        return post(POSTS, post);
    }

    //UPDATE / PUT a post
    public Response updatePost(int id, PostDto post){
        return put(POSTS +"/" + id, post);
    }

    //DELETE a post
    public Response deletePost(int id){
        return delete(POSTS + "/" + id);
    }


}
