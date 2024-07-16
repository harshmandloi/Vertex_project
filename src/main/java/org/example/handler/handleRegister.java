package org.example.handler;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.example.entity.Entites;
import org.example.repositary.Repo;
import org.mindrot.jbcrypt.BCrypt;


public enum handleRegister implements Handler<RoutingContext> {
    INSTANCE;

    private Repo userRepository;

    public static void init(Repo userRepository) {
        INSTANCE.userRepository = userRepository;
    }

    @Override
    public void handle(RoutingContext routingContext) {

        JsonObject body = routingContext.getBodyAsJson();
        if (body == null) {
            routingContext.response().setStatusCode(400).end("Invalid input");
            return;
        }

        String name = body.getString("name");
        String password = body.getString("password");

        System.out.println("Starting the function");
        if (name == null || password == null) {
            routingContext.response().setStatusCode(400).end("Name or password cannot be null");
            return;
        }

        System.out.println("name is the"+ name + " "  + password);

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Entites user = new Entites();
        user.setName(name);
        user.setPassword(hashedPassword);

         userRepository.save(user);

        routingContext.response().setStatusCode(201).end("User registered");
    }
}
