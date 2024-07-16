    package org.example.databaseconfig;

    import io.ebean.EbeanServer;
    import io.ebean.EbeanServerFactory;
    import io.ebean.config.ServerConfig;
    import io.ebean.datasource.DataSourceConfig;
    import org.example.entity.Entites;


    public class Db_Config {

        public static EbeanServer initializeEbeanServer(){

            ServerConfig config = new ServerConfig();
            config.setName("db");
            config.setDefaultServer(true);

            // Set these options to create and/or update tables if they don't exist
            config.setDdlCreateOnly(false); // Only create tables, don't modify existing ones
            config.setDdlGenerate(true);   // Generate necessary DDL
            config.setDdlRun(true);        // Execute DDL commands


            DataSourceConfig ds = new DataSourceConfig();
            ds.setDriver("com.mysql.cj.jdbc.Driver");
            ds.setUrl("jdbc:mysql://localhost:3306/gradle_data?createDatabaseIfNotExist=true");
            ds.setUsername("root");
            ds.setPassword("");
            config.setDataSourceConfig(ds);

            config.addClass(Entites.class);
            return EbeanServerFactory.create(config);
        }
    }