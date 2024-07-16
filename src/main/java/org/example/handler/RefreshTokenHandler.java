package org.example.handler;

import io.ebean.EbeanServer;
import io.jsonwebtoken.Claims;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.example.JWT.JWTUtils;
import org.example.entity.Entites;

public class RefreshTokenHandler implements Handler<RoutingContext> {

    private EbeanServer ebeanServer;

    public RefreshTokenHandler(EbeanServer ebeanServer) {
        this.ebeanServer = ebeanServer;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        JsonObject body = routingContext.getBodyAsJson();
        if (body == null || body.getString("refreshToken") == null) {
            routingContext.response().setStatusCode(400).end("Invalid input");
            return;
        }

        String refreshToken = body.getString("refreshToken");
        try {
            Claims claims = JWTUtils.validateToken(refreshToken);
            String username = claims.getSubject();

            Entites user = ebeanServer.find(Entites.class).where().eq("name", username).findOne();

            if (user == null || !refreshToken.equals(user.getRefreshToken())) {
                routingContext.response().setStatusCode(401).end("Unauthorized");
                return;
            }

            String newAccessToken = JWTUtils.generateAccessToken(user);
            user.setToken(newAccessToken);
            ebeanServer.save(user);

            JsonObject responseJson = new JsonObject()
                    .put("accessToken", newAccessToken);

            routingContext.response()
                    .putHeader("Content-Type", "application/json")
                    .end(responseJson.encode());
        } catch (Exception e) {
            routingContext.response().setStatusCode(401).end("Unauthorized");
        }
    }
}
