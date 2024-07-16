package org.example.globalExceptionHandling;


import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class GlobalExceptionHandling implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext context) {
        Throwable failure = context.failure();
        if (failure != null) {
            failure.printStackTrace();
            context.response()
                    .setStatusCode(500)
                    .end("Server Error: " + failure.getMessage());
        } else {
            context.response()
                    .setStatusCode(500)
                    .end("Unknown Server Error");
        }
    }
}
