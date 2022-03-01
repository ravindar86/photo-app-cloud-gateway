package com.photo.app.photoappcloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PhotoAppCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoAppCloudGatewayApplication.class, args);
    }

}
