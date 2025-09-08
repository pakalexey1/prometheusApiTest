package com.prometheus.clients;

import com.prometheus.core.BaseClient;
import com.prometheus.core.RequestSpecs;
import io.restassured.response.Response;

public class CommentsClient extends BaseClient {

    private static final String COMMENTS = "/comments";

    public CommentsClient(){
        super(RequestSpecs.defaultSpec());
    }

    //GET comment by id
    public Response getCommentById(int id){
        return get(COMMENTS + "/" + id);
    }

    //GET all comments
    public Response listComments(){
        return get(COMMENTS);
    }

    //GET comments for a post
    public Response listCommentsForPost(int postId){
        return get("/posts/" + postId + COMMENTS);
    }

    //CREATE/POST a comment
    public Response createComment(Object body){
        return post(COMMENTS, body);
    }

    //UPDATE/PUT a comment
    public Response updateComment(int id, Object body){
        return put (COMMENTS + "/" + id, body);
    }

    //DELETE a comment
    public Response deleteComment(int id){
        return delete (COMMENTS + "/" + id);
    }
}
