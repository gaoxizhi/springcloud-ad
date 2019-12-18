package net.gaox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/18 16:12
 */
@EnableFeignClients
@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class SponsorApplication {

    public static void main(String[] args) {

        SpringApplication.run(SponsorApplication.class, args);
    }
}