package com.prometheus.clients;

import com.prometheus.core.BaseClient;
import com.prometheus.core.RequestSpecs;

public class CommentsClient extends BaseClient {

    private static final String COMMENTS = "/comments";

    public CommentsClient(){
        super(RequestSpecs.defaultSpec());
    }
}
