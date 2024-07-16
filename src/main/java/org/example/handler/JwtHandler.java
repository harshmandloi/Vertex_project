package org.example.handler;

import io.jsonwebtoken.Claims;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.example.JWT.JWTUtils;

public class JwtHandler implements Handler<RoutingContext> {

    public static JwtHandler create() {
        return new JwtHandler();
    }

    @Override
    public void handle(RoutingContext routingContext) {
        String authHeader = routingContext.request().getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            routingContext.response().setStatusCode(401).end("Unauthorized");
            return;
        }

        String token = authHeader.substring(7);
        try {
            Claims claims = JWTUtils.validateToken(token);
            routingContext.put("user", claims.getSubject());
            routingContext.next();
        } catch (Exception e) {
            routingContext.response().setStatusCode(401).end("Unauthorized");
        }
    }
}
