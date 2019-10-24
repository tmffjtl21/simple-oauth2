package com.tjlee.oauth2;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimpleOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(SimpleOauth2Application.class, args);
    }

    // h2콘솔을 보기위한 옵션
    @Bean
    org.h2.tools.Server h2Server() {
        Server server = new Server();
        try {
            server.runTool("-tcp");
            server.runTool("-tcpAllowOthers");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return server;
    }

}
