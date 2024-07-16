package org.example.handler;

import io.ebean.EbeanServer;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.example.JWT.JWTUtils;
import org.example.entity.Entites;
import org.example.repositary.Repo;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;

public enum handleLogin implements Handler<RoutingContext> {
    INSTANCE;

    private Repo userRepository;
    private EbeanServer ebeanServer;

    public static void init(Repo userRepository, EbeanServer ebeanServer) {
        INSTANCE.userRepository = userRepository;
        INSTANCE.ebeanServer = ebeanServer;
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

        if (name == null || password == null) {
            routingContext.response().setStatusCode(400).end("Name or password cannot be null");
            return;
        }

        Entites user = ebeanServer.find(Entites.class).where().eq("name", name).findOne();

        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            routingContext.response().setStatusCode(401).end("Invalid credentials");
            return;
        }

        String accessToken = JWTUtils.generateAccessToken(user);
        String refreshToken = JWTUtils.generateRefreshToken(user);

        user.setToken(accessToken);
        user.setRefreshToken(refreshToken);
        user.setTokenExpiry(new Date(System.currentTimeMillis() + JWTUtils.ACCESS_TOKEN_EXPIRY)); // Define ACCESS_TOKEN_EXPIRY in JWTUtils
        user.setRefreshTokenExpiry(new Date(System.currentTimeMillis() + JWTUtils.REFRESH_TOKEN_EXPIRY)); // Define REFRESH_TOKEN_EXPIRY in JWTUtils

        ebeanServer.save(user);

        JsonObject responseJson = new JsonObject()
                .put("accessToken", accessToken)
                .put("refreshToken", refreshToken);

        routingContext.response()
                .putHeader("Content-Type", "application/json")
                .end(responseJson.encode());
    }
}
