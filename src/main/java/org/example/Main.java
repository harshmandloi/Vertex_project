package org.example;

import io.ebean.EbeanServer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Retrofit.RetrofitPostHandler;
import org.example.databaseconfig.Db_Config;
import org.example.databaseconfig.controller.usercontroller;
import org.example.globalExceptionHandling.GlobalExceptionHandling;
import org.example.handler.*;
import org.example.handler.TokenSchedular;
import org.example.repoimplementation.Repoimpl;
import org.example.repositary.Repo;
import org.quartz.*;


public class Main extends AbstractVerticle {

    private static final Logger logger = LogManager.getLogger(Main.class);


    private Repo userRepository;
    private  EbeanServer ebeanServer;

    public Main(Repo userRepository, EbeanServer ebeanServer) {
        this.userRepository = userRepository;
        this.ebeanServer = ebeanServer;
    }

    @Override
    public void start() {
        logger.info("Verticle started .............. dfgdfgdfgdfgd");


        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        CreateEmployeesHandler.init(userRepository);
        DeleteEmployeeHandler.init(userRepository);
        GetEmployeeHandler.init(userRepository);
        GetEmployeesHandler.init(userRepository);
        handleRegister.init(userRepository);
        handleLogin.init(userRepository,ebeanServer);
        TokenSchedular.init(ebeanServer);
        RetrofitPostHandler.init();

        usercontroller.INSTANCE.router(router);

        router.route("/secure/*").handler(JwtHandler.create());

        router.get("/secure/data").handler(routingContext -> {
            routingContext.response().end("This is secured data.");
        });

        router.route().failureHandler(new GlobalExceptionHandling());

        vertx.setPeriodic(3600000, id -> {
            System.out.println("Running token refresh job...");
        });

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8888, result -> {
                    if (result.succeeded()) {
                        System.out.println("Server is now listening on port 8888");
                    } else {
                        System.err.println("Failed to bind!");
                    }
                });
    }


    public static void main(String[] args) throws SchedulerException {
        Vertx vertx = Vertx.vertx();            // Initialize Vert.x
        EbeanServer ebeanServer = Db_Config.initializeEbeanServer();
        Repo userRepository = new Repoimpl(ebeanServer);       // Initialize your repository
        Main mainVerticle = new Main(userRepository, ebeanServer);  // Initialize your main Verticle with the repository
        vertx.deployVerticle(mainVerticle);

//        System.out.println("Hello world!");
//        JobDetail job = JobBuilder.newJob(MyJob.class)
//                .withIdentity("myJob", "group1")
//                .build();
//
//        // Trigger to run every minute
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity("myTrigger", "group1")
//                .startNow()
//                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(10))
//                .build();
//
//        // Scheduler
//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//        scheduler.start();
//        scheduler.scheduleJob(job, trigger);
//
//        System.out.println("Scheduler started...");
    }
}

