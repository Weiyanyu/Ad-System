package top.yeonon.adsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringCloudApplication
//@EnableEurekaClient
@EnableFeignClients
@EnableHystrixDashboard
@ComponentScan(basePackages = {"top.yeonon.adcommon", "top.yeonon.adsearch"})
public class AdSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdSearchApplication.class, args);
    }

}
