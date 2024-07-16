package org.example.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.example.entity.Entites;
import org.example.repositary.Repo;

public enum DeleteEmployeeHandler implements Handler<RoutingContext> {
    INSTANCE;
    private Repo userRepository;

    public static void init(Repo userRepository) {
        INSTANCE.userRepository = userRepository;
    }
    @Override
    public void handle(RoutingContext routingContext) {
        Long id = Long.valueOf((routingContext.pathParam("id")));
        Entites user = userRepository.findById(id);
        if (user != null) {
            userRepository.delete(user);
            routingContext.response().setStatusCode(204).end("Successfully Deleted");
        } else {
            routingContext.response().setStatusCode(404).end("User not found");
        }
    }
}
