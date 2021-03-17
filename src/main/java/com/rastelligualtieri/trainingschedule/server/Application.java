package com.rastelligualtieri.trainingschedule.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


//@EnableScheduling
@EnableJpaRepositories
//@EnableAsync
//@EnableAspectJAutoProxy
@SpringBootApplication
public class Application {

    /**
     * Method to run the application
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
