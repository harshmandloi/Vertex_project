package org.example.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.example.entity.Entites;
import org.example.repositary.Repo;

public enum CreateEmployeesHandler implements Handler<RoutingContext> {

    INSTANCE;
    private Repo userRepository;

    public static void init(Repo userRepository) {
        INSTANCE.userRepository = userRepository;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        System.out.println( "startin the function");

        System.out.println( "startin the function2");
        String name = String.valueOf(routingContext.pathParam("name"));
        System.out.println( "startin the function3");
        String password = String.valueOf(routingContext.pathParam("password"));        System.out.println( "startin the function4");
        if(name==null || password==null){
            routingContext.response().setStatusCode(400).end("Failed");
        }
        System.out.println( "startin the function5");
        Entites et=new Entites();
        et.setName(name);
        et.setPassword(password);
        userRepository.save(et);
        routingContext.response().setStatusCode(200).end("Success");
    }
}
