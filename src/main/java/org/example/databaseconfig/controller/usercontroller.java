package org.example.databaseconfig.controller;

import io.ebean.EbeanServer;
import io.vertx.core.Handler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.example.Retrofit.RetrofitPostHandler;
import org.example.handler.*;


public enum usercontroller implements Handler<RoutingContext> {

    INSTANCE, RefreshTokenHandler;
    private EbeanServer ebeanServer;



//    public void initHandlers(repo userRepository) {
//        System.out.println("hllo1");
//        CreateEmployeesHandler.init(userRepository);
//        DeleteEmployeeHandler.init(userRepository);
//        GetEmployeeHandler.init(userRepository);
//        GetEmployeesHandler.init(userRepository);
//        handleRegister.init(userRepository);
//        handleLogin.init(userRepository);
//    }

    public void router(Router router){

        router.post("/employee").handler(JwtHandler.create()).handler(CreateEmployeesHandler.INSTANCE);
        router.delete("/employee/:id").handler(JwtHandler.create()).handler(DeleteEmployeeHandler.INSTANCE);
        router.get("/employee/:id").handler(JwtHandler.create()).handler(GetEmployeeHandler.INSTANCE);
        router.get("/employees").handler(JwtHandler.create()).handler(GetEmployeesHandler.INSTANCE);
        router.post("/register").handler(handleRegister.INSTANCE);
        router.post("/refresh_token").handler(new RefreshTokenHandler(ebeanServer));
        router.post("/login").handler(handleLogin.INSTANCE);
        router.get("/RetrofitPost").handler(RetrofitPostHandler.INSTANCE);


    }

    @Override
    public void handle(RoutingContext event) {

    }
}
