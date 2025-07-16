package com.tang.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient // 标注类是消费者
@SpringBootApplication
//把调用注册子模块接口引入到Spring容器中（不加此注解会出现找不到@FeignClient修饰的接口）
@EnableFeignClients
@EnableCircuitBreaker //启动 Hystrix 【另外Feign开启熔断器方式不熟悉】
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
