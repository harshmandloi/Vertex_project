package org.example.Retrofit;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Main;
import org.example.entity.Entites;
import org.example.repositary.Repo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public enum RetrofitPostHandler implements Handler<RoutingContext> {

    INSTANCE;

    private static final Logger logger = LogManager.getLogger(RetrofitPostHandler.class);

    public static void init() {
    }


    @Override
    public void handle(RoutingContext routingContext) {
        logger.info("inside the handler");

        List<Post> display=new ArrayList<>();

        JsonPlaceholderApi jsonplaceholderapi = RetrofitInstance.getRetrofitInstance().create(JsonPlaceholderApi.class);
        Call<List<Post>> callAsync = jsonplaceholderapi.getPost();

        logger.info("inside the handler1");

        try {
            Response<List<Post>> response = callAsync.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logger.info("inside the handler3");

        try {
            List<Post> response = callAsync.execute().body();
            logger.info("inside the handler4");
            routingContext.response()
                            .putHeader("Content-Type", "application/json")
                            .setStatusCode(200)
                            .end(response.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        callAsync.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                logger.info("inside the handler4");
//                if (response.isSuccessful()) {
//                    logger.info("inside the handler5");
//                    List<Post> users = response.body();
//                    routingContext.response()
//                            .putHeader("Content-Type", "application/json")
//                            .setStatusCode(200)
//                            .end(users.toString());
//                } else {
//                    routingContext.response().setStatusCode(200).end("ListEmpty");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                routingContext.response().setStatusCode(200).end("Failed");
//                logger.error("Network request failed", t);
//            }
//        });

    }
}
