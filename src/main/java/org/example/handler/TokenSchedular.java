package org.example.handler;


import io.ebean.EbeanServer;
import io.jsonwebtoken.Claims;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import org.example.JWT.JWTUtils;
import org.example.entity.Entites;

import java.util.Date;
import java.util.List;

public enum TokenSchedular implements Handler<RoutingContext> {

    INSTANCE;

    private EbeanServer ebeanServer;

    public static void init(EbeanServer ebeanServer) {
        INSTANCE.ebeanServer = ebeanServer;
    }

    public void start() {

        long interval = 3600000;

        Vertx vertx = Vertx.vertx(); // or pass your existing vertx instance
        vertx.setPeriodic(interval, id -> {
            regenerateTokens();
        });
    }

    private void regenerateTokens() {
        Date now = new Date();
        List<Entites> users = ebeanServer.find(Entites.class)
                .where()
                .lt("tokenExpiry", now)
                .findList();

        for (Entites user : users) {
            try {
                Claims claims = JWTUtils.validateToken(user.getRefreshToken());
                String username = claims.getSubject();

                if (username.equals(user.getName()) && user.getRefreshTokenExpiry().after(now)) {
                    String newAccessToken = JWTUtils.generateAccessToken(user);
                    user.setToken(newAccessToken);
                    user.setTokenExpiry(new Date(System.currentTimeMillis() + JWTUtils.ACCESS_TOKEN_EXPIRY));


                    ebeanServer.save(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handle(RoutingContext event) {

    }
}
