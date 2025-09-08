package com.prometheus.clients;

import com.prometheus.core.BaseClient;
import com.prometheus.core.RequestSpecs;
import io.restassured.response.Response;

public class UsersClient extends BaseClient {

    private static final String USERS = "/users";

    public UsersClient(){
        super(RequestSpecs.defaultSpec());
    }

    //GET all users
    public Response getAllUsers(){
        return get(USERS);
    }

    //GET a user by an ID
    public Response getUserById(int id){
        return get(USERS + "/" + id);
    }

    //CREATE / POST a user
    public Response createUser(Object body){
        return post(USERS, body);
    }

    //UPDATE/ PUT a user
    public Response updateUser(int id, Object body){
        return put(USERS + "/" + id, body);
    }

    //DELETE a user
    public Response deleteUser(int id){
        return delete(USERS + "/" +id);
    }

}
