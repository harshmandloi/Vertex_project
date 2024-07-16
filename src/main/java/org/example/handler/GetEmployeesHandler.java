package org.example.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.example.entity.Entites;
import org.example.repositary.Repo;
import org.example.response.entitesResponse;

import java.util.List;

public enum GetEmployeesHandler implements Handler<RoutingContext> {

    INSTANCE;

    private Repo userRepository;

    public static void init(Repo userRepository) {
        INSTANCE.userRepository = userRepository;
    }

    @Override
    public void handle(RoutingContext routingContext) {

        List<Entites> user = userRepository.findAll();
        entitesResponse er = new entitesResponse();
        er.setEmployeesList(user);
        if (user != null) {
            routingContext.response()
                    .putHeader("Content-Type", "application/json")
                    .setStatusCode(200)
                    .end(er.toJsonObject().encode());

        } else {
            routingContext.response().setStatusCode(404).end("User not found");
        }

    }
}
